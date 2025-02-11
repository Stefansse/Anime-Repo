package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Studio;
import com.AnimeApp.model.dto.mainDTOs.StudioDTO;
import org.springframework.stereotype.Component;

@Component
public class StudioMapper {

    public StudioDTO toDTO(Studio studio) {
        return new StudioDTO(
                studio.getId(),
                studio.getName(),
                studio.getDescription(),
                studio.getNum_of_animes()
        );
    }

    public Studio fromDTO(StudioDTO studioDTO) {
        Studio studio = new Studio();
        studio.setId(studioDTO.getId());
        studio.setName(studioDTO.getName());
        studio.setDescription(studioDTO.getDescription());
        studio.setNum_of_animes(studioDTO.getNumOfAnimes());

        return studio;
    }
}
