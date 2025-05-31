package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Anime;
import com.AnimeApp.model.Manga;
import com.AnimeApp.model.dto.mainDTOs.AnimeDTO;
import com.AnimeApp.model.elastic.AnimeSearch;
import com.AnimeApp.model.exceptions.AnimeNotFoundException;
import com.AnimeApp.model.exceptions.AuthorNotFoundException;
import com.AnimeApp.model.mappers.AnimeMapper;
import com.AnimeApp.repository.*;
import com.AnimeApp.service.Minio.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final AnimeMapper animeMapper;
    private final AuthorRepository authorRepository;
    private final StudioRepository studioRepository;
    private final MangaRepository mangaRepository;
    private final MinioService minioService;
    private final AnimeSearchRepository animeSearchRepository;

    private final ElasticsearchTemplate elasticsearchRestTemplate;


    @Autowired
    public AnimeService(AnimeRepository animeRepository, AnimeMapper animeMapper,
                        AuthorRepository authorRepository, StudioRepository studioRepository,
                        MangaRepository mangaRepository, MinioService minioService,
                        AnimeSearchRepository animeSearchRepository, ElasticsearchTemplate elasticsearchRestTemplate) {
        this.animeRepository = animeRepository;
        this.animeMapper = animeMapper;
        this.authorRepository = authorRepository;
        this.studioRepository = studioRepository;
        this.mangaRepository = mangaRepository;
        this.minioService = minioService;
        this.animeSearchRepository = animeSearchRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    // Convert Anime entity to Elasticsearch AnimeSearch document
    private AnimeSearch convertToAnimeSearch(Anime anime) {
        AnimeSearch animeSearch = new AnimeSearch();
        animeSearch.setId(String.valueOf(anime.getId()));
        animeSearch.setName(anime.getName());
        animeSearch.setGenre(anime.getGenre() != null ? anime.getGenre().name() : null);
        animeSearch.setType(anime.getType() != null ? anime.getType().name() : null);
        animeSearch.setRating(anime.getRating());
        animeSearch.setStudioId(anime.getStudios() != null && !anime.getStudios().isEmpty() ?
                anime.getStudios().iterator().next().getId() : null);
        return animeSearch;
    }


    public List<AnimeDTO> getAllAnime() {
        List<Anime> animeList = animeRepository.findAll();
        return animeList.stream()
                .map(animeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AnimeDTO getAnimeById(Long id) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new AnimeNotFoundException(id));
        return animeMapper.toDTO(anime);
    }

    @Transactional
    public AnimeDTO createAnime(AnimeDTO animeDTO) {
        if (animeDTO.getImageFile() != null && !animeDTO.getImageFile().isEmpty()) {
            String fileName = minioService.uploadFile(animeDTO.getImageFile());
            animeDTO.setAnimeImage(fileName);
        }

        Anime anime = animeMapper.fromDTO(animeDTO);

        if (animeDTO.getAuthorId() != null) {
            anime.setAuthor(authorRepository.findById(animeDTO.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException(animeDTO.getAuthorId())));
        }

        if (animeDTO.getMangaId() != null) {
            Manga manga = mangaRepository.findById(animeDTO.getMangaId())
                    .orElseThrow(() -> new RuntimeException("Manga not found with id: " + animeDTO.getMangaId()));
            anime.setManga(manga);
        }

        if (animeDTO.getStudioNames() != null) {
            anime.setStudios(studioRepository.findByNameIn(animeDTO.getStudioNames()));
        }

        anime = animeRepository.save(anime);

        // Sync to Elasticsearch
        animeSearchRepository.save(convertToAnimeSearch(anime));

        return animeMapper.toDTO(anime);
    }

    @Transactional
    public AnimeDTO updateAnime(Long id, AnimeDTO animeDTO) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new AnimeNotFoundException(id));

        anime.setName(animeDTO.getName());
        anime.setPlot(animeDTO.getPlot());
        anime.setDateAired(animeDTO.getDateAired());
        anime.setNumOfEpisodes(animeDTO.getNumOfEpisodes());
        anime.setDuration(animeDTO.getDuration());
        anime.setRating(animeDTO.getRating());
        anime.setGenre(animeDTO.getGenre());
        anime.setType(animeDTO.getType());
        anime.setAnimeImage(animeDTO.getAnimeImage());

        if (animeDTO.getAuthorId() != null) {
            anime.setAuthor(authorRepository.findById(animeDTO.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException(animeDTO.getAuthorId())));
        }

        if (animeDTO.getStudioNames() != null) {
            anime.setStudios(studioRepository.findByNameIn(animeDTO.getStudioNames()));
        }

        if (animeDTO.getMangaId() != null) {
            Manga manga = mangaRepository.findById(animeDTO.getMangaId())
                    .orElseThrow(() -> new RuntimeException("Manga with ID " + animeDTO.getMangaId() + " not found"));
            anime.setManga(manga);
        }

        anime = animeRepository.save(anime);

        // Sync updated Anime to Elasticsearch
        animeSearchRepository.save(convertToAnimeSearch(anime));

        return animeMapper.toDTO(anime);
    }

    @Transactional
    public void deleteAnime(Long id) {
        Anime anime = animeRepository.findById(id)
                .orElseThrow(() -> new AnimeNotFoundException(id));
        animeRepository.delete(anime);

        // Delete from Elasticsearch
        animeSearchRepository.deleteById(String.valueOf(id));
    }

    public List<AnimeDTO> getAnimesByStudioId(Long studioId) {
        List<Anime> animes = animeRepository.findByStudios_Id(studioId);
        return animes.stream()
                .map(animeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<AnimeDTO> searchAnimeInElastic(
            String name, String genre, String type,
            Double minRating, Double maxRating, Long studioId,
            int page, int size) {

        Criteria criteria = null;

        if (name != null && !name.isEmpty()) {
            Criteria nameCriteria = new Criteria("name").contains(name); // partial match substring
            criteria = (criteria == null) ? nameCriteria : criteria.and(nameCriteria);
        }

        if (genre != null && !genre.isEmpty()) {
            String genreUpper = genre.toUpperCase();
            Criteria genreCriteria = new Criteria("genre").is(genreUpper);
            criteria = (criteria == null) ? genreCriteria : criteria.and(genreCriteria);
        }

        if (type != null && !type.isEmpty()) {
            Criteria typeCriteria = new Criteria("type").is(type);
            criteria = (criteria == null) ? typeCriteria : criteria.and(typeCriteria);
        }

        if (minRating != null || maxRating != null) {
            double from = minRating != null ? minRating : 0.0;
            double to = maxRating != null ? maxRating : 10.0;  // assuming max rating is 10
            Criteria ratingCriteria = new Criteria("rating").between(from, to);
            criteria = (criteria == null) ? ratingCriteria : criteria.and(ratingCriteria);
        }

        if (studioId != null) {
            Criteria studioCriteria = new Criteria("studioId").is(studioId);
            criteria = (criteria == null) ? studioCriteria : criteria.and(studioCriteria);
        }

        if (criteria == null) {
            criteria = new Criteria(); // match all if no filters
        }

        Pageable pageable = PageRequest.of(page, size);
        CriteriaQuery query = new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<AnimeSearch> searchHits = elasticsearchRestTemplate.search(query, AnimeSearch.class);

        List<AnimeDTO> dtos = searchHits.stream()
                .map(hit -> {
                    Long id = Long.valueOf(hit.getContent().getId());
                    return animeRepository.findById(id)
                            .map(animeMapper::toDTO)
                            .orElse(null);
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, searchHits.getTotalHits());
    }



    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("Starting Elasticsearch reindexing...");
        reindexAllAnimeToElastic();
        System.out.println("Finished Elasticsearch reindexing.");
    }

    public void reindexAllAnimeToElastic() {
        List<Anime> allAnime = animeRepository.findAllWithStudios(); // eager fetch studios
        List<AnimeSearch> animeSearchList = allAnime.stream()
                .map(this::convertToAnimeSearch)
                .collect(Collectors.toList());

        animeSearchRepository.deleteAll();
        animeSearchRepository.saveAll(animeSearchList);
    }

}
