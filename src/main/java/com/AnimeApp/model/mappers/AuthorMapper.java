package com.AnimeApp.model.mappers;


import com.AnimeApp.model.Author;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getCountry(),
                author.getBirthDate()
        );
    }

    public Author fromDTO(AuthorDTO authorDTO) {
        Author author = new Author();

        author.setId(authorDTO.getId());
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setCountry(authorDTO.getCountry());
        author.setBirthDate(authorDTO.getBirthDate());

        return author;
    }
}
