package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Studio;
import com.AnimeApp.model.dto.mainDTOs.AnimeDTO;
import com.AnimeApp.model.dto.mainDTOs.AnimeSimpleDTO;
import com.AnimeApp.model.dto.mainDTOs.StudioDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudioMapper {

    private final AnimeMapper animeMapper;

    public StudioMapper(AnimeMapper animeMapper) {
        this.animeMapper = animeMapper;
    }

    public StudioDTO toDTO(Studio studio) {
        List<AnimeDTO> animeDTOs = null;
        if (studio.getAnimes() != null) {
            animeDTOs = studio.getAnimes()
                    .stream()
                    .map(animeMapper::toDTO)
                    .collect(Collectors.toList());
        }

        StudioDTO dto = new StudioDTO(
                studio.getId(),
                studio.getName(),
                studio.getDescription(),
                studio.getNum_of_animes(),
                studio.getStudioImage()
        );
        dto.setAnimes(animeDTOs);  // set the list of AnimeDTOs

        return dto;
    }

    public Studio fromDTO(StudioDTO dto) {
        Studio studio = new Studio();
        studio.setId(dto.getId());
        studio.setName(dto.getName());
        studio.setDescription(dto.getDescription());
        studio.setNum_of_animes(dto.getNumOfAnimes());
        studio.setStudioImage(dto.getStudioImage());

        // Usually you don't set Animes here unless you're updating with full info
        // You could add that logic if needed

        return studio;
    }
}
