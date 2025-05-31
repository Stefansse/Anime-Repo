package com.AnimeApp.model.dto.mainDTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String country;

    private LocalDate birthDate;

    private List<AnimeSimpleDTO> animes;

    public AuthorDTO(Long id, String firstName, String lastName, String country, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.birthDate = birthDate;
    }
}
