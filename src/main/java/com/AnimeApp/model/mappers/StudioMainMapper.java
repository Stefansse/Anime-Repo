package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Studio;
import com.AnimeApp.model.dto.mainDTOs.StudioMainDTO;
import org.springframework.stereotype.Component;

@Component
public class StudioMainMapper {

    // Map Studio entity to StudioMainDTO
    public StudioMainDTO toDTO(Studio studio) {
        if (studio == null) {
            return null;
        }
        return new StudioMainDTO(
                studio.getId(),
                studio.getName(),
                studio.getDescription(),
                studio.getNum_of_animes(),
                studio.getStudioImage()
        );
    }

    // Map StudioMainDTO to Studio entity
    public Studio fromDTO(StudioMainDTO dto) {
        if (dto == null) {
            return null;
        }
        Studio studio = new Studio();
        studio.setId(dto.getId());
        studio.setName(dto.getName());
        studio.setDescription(dto.getDescription());
        studio.setNum_of_animes(dto.getNumOfAnimes());
        studio.setStudioImage(dto.getStudioImage());
        return studio;
    }
}
