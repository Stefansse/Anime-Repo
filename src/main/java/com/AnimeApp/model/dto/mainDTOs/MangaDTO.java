package com.AnimeApp.model.dto.mainDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MangaDTO {
    private Long id;
    private String name;
    private LocalDate dateAired;  // Matches entity field name
    private Integer vol;
    private Double rating;
    private Long authorId;
    private String authorName;
    // Author of the manga
    // Optional: include anime info if needed
    // private AnimeDTO anime;

    public MangaDTO(Long id, String name, LocalDate dateAired, Integer vol, Double rating, Long authorId, String authorName) {
        this.id = id;
        this.name = name;
        this.dateAired = dateAired;
        this.vol = vol;
        this.rating = rating;
        this.authorId = authorId;
        this.authorName = authorName;
    }
}
