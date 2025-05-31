package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Author;
import com.AnimeApp.model.dto.mainDTOs.AnimeSimpleDTO;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    public AuthorDTO toDTO(Author author) {
        // Map basic author fields
        AuthorDTO dto = new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getCountry(),
                author.getBirthDate()
        );

        // ✅ Map the list of animes to AnimeSimpleDTOs
        if (author.getAnimes() != null) {
            List<AnimeSimpleDTO> animeList = author.getAnimes().stream()
                    .map(anime -> new AnimeSimpleDTO(anime.getId(), anime.getName(),anime.getRating(), anime.getDuration(), anime.getAnimeImage()))
                    .collect(Collectors.toList());

            dto.setAnimes(animeList);

            dto.setAnimes(animeList); // ✅ Attach to DTO
        }

        return dto;
    }

    public Author fromDTO(AuthorDTO dto) {
        Author author = new Author();
        author.setId(dto.getId());  // Needed for updates
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setCountry(dto.getCountry());
        author.setBirthDate(dto.getBirthDate());

        return author;
    }
}
