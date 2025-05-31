package com.AnimeApp.model.dto.mainDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnimeSimpleDTO {
    private Long id;
    private String name;
    private Integer rating;
    private Integer duration;
    private String animeImage;// You can also call this name
}