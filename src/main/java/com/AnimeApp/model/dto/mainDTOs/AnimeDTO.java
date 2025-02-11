package com.AnimeApp.model.dto.mainDTOs;

import com.AnimeApp.model.enumerations.Genre;
import com.AnimeApp.model.enumerations.Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnimeDTO {
    private Long id;
    private String name;
    private LocalDate releaseDate;
    private Integer rating;
    private Genre genre;
    private Type type;
    private Integer numOfEpisodes; // Keep for the number of episodes
    private Integer duration; // Keep for duration


    private AuthorDTO author;

    public AnimeDTO(Long id, String name,LocalDate releaseDate, Integer rating, Genre genre, Type type
    , Integer numOfEpisodes, Integer duration, AuthorDTO author) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.genre = genre;
        this.type = type;
        this.numOfEpisodes = numOfEpisodes;
        this.duration = duration;
        this.author = author;
    }
}
