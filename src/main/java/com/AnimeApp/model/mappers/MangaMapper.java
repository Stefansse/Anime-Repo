package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Manga;
import com.AnimeApp.model.dto.mainDTOs.MangaDTO;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class MangaMapper {

    private final AuthorMapper authorMapper;

    public MangaMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    public MangaDTO toDTO(Manga manga) {
        AuthorDTO authorDTO = authorMapper.toDTO(manga.getAuthorM());

        return new MangaDTO(
                manga.getId(),                // Mapping the id
                manga.getName(),              // Mapping the name
                manga.getDateAired(),         // Mapping the releaseDate
                manga.getVol(),               // Mapping the volume (vol)
                manga.getRating(),            // Mapping the rating
                authorDTO                     // Mapping the AuthorDTO
        );
    }

    public Manga fromDTO(MangaDTO mangaDTO) {
        Manga manga = new Manga();
        manga.setId(mangaDTO.getId());        // Setting the id if needed for updates
        manga.setName(mangaDTO.getName());
        manga.setDateAired(mangaDTO.getReleaseDate());
        manga.setVol(mangaDTO.getVol());
        manga.setRating(mangaDTO.getRating());

        if (mangaDTO.getAuthor() != null) {
            manga.setAuthorM(authorMapper.fromDTO(mangaDTO.getAuthor()));
        }

        return manga;
    }
}
