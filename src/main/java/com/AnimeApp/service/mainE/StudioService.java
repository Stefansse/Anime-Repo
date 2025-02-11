package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Studio;
import com.AnimeApp.model.dto.mainDTOs.StudioDTO;
import com.AnimeApp.model.exceptions.StudioNotFoundException;
import com.AnimeApp.model.mappers.StudioMapper;
import com.AnimeApp.repository.StudioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudioService {

    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;

    public StudioService(StudioRepository studioRepository, StudioMapper studioMapper) {
        this.studioRepository = studioRepository;
        this.studioMapper = studioMapper;
    }

    // Get Studio by ID and return as StudioDTO
    public StudioDTO getStudioById(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new StudioNotFoundException(id)); // Throw custom exception
        return studioMapper.toDTO(studio);
    }

    // Create a new Studio from StudioDTO
    @Transactional
    public StudioDTO createStudio(StudioDTO studioDTO) {
        Studio studio = studioMapper.fromDTO(studioDTO);
        studio = studioRepository.save(studio);
        return studioMapper.toDTO(studio);
    }

    // Update an existing Studio from StudioDTO
    @Transactional
    public StudioDTO updateStudio(Long id, StudioDTO studioDTO) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new StudioNotFoundException(id)); // Throw custom exception

        // Set updated values from the DTO
        studio.setName(studioDTO.getName());
        studio.setDescription(studioDTO.getDescription());
        studio.setNum_of_animes(studioDTO.getNumOfAnimes());

        studio = studioRepository.save(studio); // Save the updated studio
        return studioMapper.toDTO(studio); // Return the updated Studio as DTO
    }

    // Delete a Studio by ID
    @Transactional
    public void deleteStudio(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new StudioNotFoundException(id)); // Throw custom exception
        studioRepository.delete(studio);
    }
}
