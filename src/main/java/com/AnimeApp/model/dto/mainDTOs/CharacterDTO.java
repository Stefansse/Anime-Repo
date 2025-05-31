package com.AnimeApp.model.dto.mainDTOs;

import com.AnimeApp.model.Anime;
import lombok.Data;

@Data
public class CharacterDTO {

    private Long id;

    private String name;

    private String description;

    private String charImage;

    private Anime anime;

    public CharacterDTO(Long id, String name, String description, String charImage, Anime anime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.charImage = charImage;
        this.anime = anime;
    }
}
