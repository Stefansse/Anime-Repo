package com.AnimeApp.model.dto.mainDTOs;

import com.AnimeApp.model.enumerations.Genre;
import com.AnimeApp.model.enumerations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class AnimeDTO {
    private Long id;
    private String name;
    private String plot;
    private LocalDate dateAired;
    private Integer numOfEpisodes;
    private Integer duration;
    private Integer rating;
    private Genre genre;
    private Type type;

    private String animeImage; // This will now store MinIO file name or URL

    private Long mangaId;
    private Long authorId;
    private String authorName;
    private List<String> studioNames;

    @JsonIgnore // Exclude from JSON serialization
    private MultipartFile imageFile;

    public AnimeDTO(Long id, String name, String plot, LocalDate dateAired,
                    Integer numOfEpisodes, Integer duration, Integer rating,
                    Genre genre, Type type, String animeImage,
                    Long mangaId, Long authorId, String authorName, List<String> studioNames) {
        this.id = id;
        this.name = name;
        this.plot = plot;
        this.dateAired = dateAired;
        this.numOfEpisodes = numOfEpisodes;
        this.duration = duration;
        this.rating = rating;
        this.genre = genre;
        this.type = type;
        this.animeImage = animeImage;
        this.mangaId = mangaId;
        this.authorId = authorId;
        this.studioNames = studioNames;
        this.authorName = authorName;
    }

    public AnimeDTO() {
    }
}
