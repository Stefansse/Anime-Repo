package com.AnimeApp.model.dto.mainDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimeSimpleDTO {
    private Long id;
    private String name;
    private Integer rating;
    private Integer duration;
    private String animeImage;// You can also call this name
}