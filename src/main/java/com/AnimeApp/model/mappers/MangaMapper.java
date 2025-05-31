package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Manga;
import com.AnimeApp.model.dto.mainDTOs.MangaDTO;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import com.AnimeApp.repository.AuthorRepository;
import org.springframework.stereotype.Component;

@Component
public class MangaMapper {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    public MangaMapper(AuthorMapper authorMapper, AuthorRepository authorRepository) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }

    public MangaDTO toDTO(Manga manga) {
        AuthorDTO authorDTO = authorMapper.toDTO(manga.getAuthorM());
        return new MangaDTO(
                manga.getId(),
                manga.getName(),
                manga.getDateAired(),
                manga.getVol(),
                manga.getRating(),
                manga.getAuthorM().getId(),
                manga.getAuthorM().getFirstName() + ' ' + manga.getAuthorM().getLastName()
        );
    }

    public Manga fromDTO(MangaDTO dto) {
        Manga manga = new Manga();
        manga.setId(dto.getId());
        manga.setName(dto.getName());
        manga.setDateAired(dto.getDateAired());
        manga.setVol(dto.getVol());
        manga.setRating(dto.getRating());

        manga.setAuthorM(authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + dto.getAuthorId())));

        return manga;
    }
}
