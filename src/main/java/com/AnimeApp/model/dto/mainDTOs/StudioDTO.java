package com.AnimeApp.model.dto.mainDTOs;

import lombok.Data;

import java.util.List;

@Data
public class StudioDTO {

    private Long id;
    private String name;
    private String description;
    private Integer numOfAnimes;
    private String studioImage; // Add image support (Base64)

    private List<AnimeDTO> animes;

    public StudioDTO(Long id, String name, String description, Integer numOfAnimes, String studioImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numOfAnimes = numOfAnimes;
        this.studioImage = studioImage;
    }
}
