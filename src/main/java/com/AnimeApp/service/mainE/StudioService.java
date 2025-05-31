package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Studio;
import com.AnimeApp.model.dto.mainDTOs.AnimeDTO;
import com.AnimeApp.model.dto.mainDTOs.StudioDTO;
import com.AnimeApp.model.dto.mainDTOs.StudioMainDTO;
import com.AnimeApp.model.exceptions.StudioNotFoundException;
import com.AnimeApp.model.mappers.AnimeMapper;
import com.AnimeApp.model.mappers.StudioMainMapper;
import com.AnimeApp.model.mappers.StudioMapper;
import com.AnimeApp.repository.StudioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudioService {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;
    private final StudioMainMapper studioMainMapper;
    private final AnimeMapper animeMapper;

    public StudioService(StudioRepository studioRepository,
                         StudioMapper studioMapper,
                         AnimeMapper animeMapper,
                         StudioMainMapper studioMainMapper) {
        this.studioRepository = studioRepository;
        this.studioMapper = studioMapper;
        this.animeMapper = animeMapper;
        this.studioMainMapper = studioMainMapper;
    }

    // Return lightweight DTO list without anime list for all studios
    public List<StudioMainDTO> getAllStudios() {
        return studioRepository.findAll().stream()
                .map(studioMainMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Return full StudioDTO with animes for single studio


    // Other methods stay with StudioDTO
    public StudioDTO getStudioById(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new StudioNotFoundException(id));
        return studioMapper.toDTO(studio);
    }

    public StudioDTO createStudio(StudioDTO studioDTO) {
        Studio studio = studioMapper.fromDTO(studioDTO);
        studio = studioRepository.save(studio);
        return studioMapper.toDTO(studio);
    }

    @Transactional
    public StudioDTO updateStudio(Long id, StudioDTO studioDTO) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new StudioNotFoundException(id));

        studio.setName(studioDTO.getName());
        studio.setDescription(studioDTO.getDescription());
        studio.setNum_of_animes(studioDTO.getNumOfAnimes());
        studio.setStudioImage(studioDTO.getStudioImage());

        studio = studioRepository.save(studio);
        return studioMapper.toDTO(studio);
    }

    @Transactional
    public void deleteStudio(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new StudioNotFoundException(id));
        studioRepository.delete(studio);
    }
}
