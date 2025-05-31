package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Anime;
import com.AnimeApp.model.Studio;
import com.AnimeApp.model.dto.mainDTOs.AnimeDTO;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import com.AnimeApp.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimeMapper {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    public AnimeMapper(AuthorMapper authorMapper, AuthorRepository authorRepository) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }

    // Convert Anime entity to AnimeDTO
    public AnimeDTO toDTO(Anime anime) {
        AuthorDTO authorDTO = authorMapper.toDTO(anime.getAuthor());

        Long mangaId = anime.getManga() != null ? anime.getManga().getId() : null;


        List<String> studioNames = anime.getStudios() != null
                ? anime.getStudios().stream()
                .map(Studio::getName)
                .collect(Collectors.toList())
                : null;

        return new AnimeDTO(
                anime.getId(),
                anime.getName(),
                anime.getPlot(),
                anime.getDateAired(),
                anime.getNumOfEpisodes(),
                anime.getDuration(),
                anime.getRating(),
                anime.getGenre(),
                anime.getType(),
                anime.getAnimeImage(),
                anime.getManga().getId(),
                anime.getAuthor().getId(),
                anime.getAuthor().getFirstName() + ' ' + anime.getAuthor().getLastName(),
                studioNames
        );
    }

    // Convert AnimeDTO to Anime entity (optional, in case of updates)
    public Anime fromDTO(AnimeDTO dto) {
        Anime anime = new Anime();
        anime.setId(dto.getId());
        anime.setName(dto.getName());
        anime.setPlot(dto.getPlot());
        anime.setDateAired(dto.getDateAired());
        anime.setNumOfEpisodes(dto.getNumOfEpisodes());
        anime.setDuration(dto.getDuration());
        anime.setRating(dto.getRating());
        anime.setGenre(dto.getGenre());
        anime.setType(dto.getType());
        anime.setAnimeImage(dto.getAnimeImage());

        anime.setAuthor(authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + dto.getAuthorId())));
        // Note: manga and studios should be set externally (e.g., in service)
        // because setting them requires fetching entities from the DB

        return anime;
    }
}
