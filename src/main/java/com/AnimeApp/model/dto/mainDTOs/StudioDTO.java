package com.AnimeApp.model.dto.mainDTOs;

import lombok.Data;

@Data
public class StudioDTO {

    private Long id;

    private String name;

    private String description;

    private Integer numOfAnimes;

    public StudioDTO(Long id, String name, String description, Integer numOfAnimes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numOfAnimes = numOfAnimes;
    }
}
