package com.AnimeApp.model.dto.mainDTOs;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MangaDTO {
    private Long id;

    private String name;

    private LocalDate releaseDate;

    private Integer vol;

    private Double rating;

    private AuthorDTO author;



    public MangaDTO(Long id, String name, LocalDate releaseDate, Integer vol, Double rating, AuthorDTO author) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.vol = vol;
        this.rating = rating;
        this.author = author;
    }


}
