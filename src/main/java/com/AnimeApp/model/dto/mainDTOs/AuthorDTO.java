package com.AnimeApp.model.dto.mainDTOs;


import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String country;

    private LocalDate birthDate;

    public AuthorDTO(Long id, String firstName, String lastName, String country, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.birthDate = birthDate;
    }
}
