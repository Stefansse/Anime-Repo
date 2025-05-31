package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Manga;
import com.AnimeApp.model.dto.mainDTOs.CharacterDTO;
import com.AnimeApp.model.dto.mainDTOs.MangaDTO;
import com.AnimeApp.model.exceptions.MangaNotFoundException;
import com.AnimeApp.model.mappers.MangaMapper;
import com.AnimeApp.repository.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MangaService {

    private final MangaRepository mangaRepository;
    private final MangaMapper mangaMapper;

    @Autowired
    public MangaService(MangaRepository mangaRepository, MangaMapper mangaMapper) {
        this.mangaRepository = mangaRepository;
        this.mangaMapper = mangaMapper;
    }


    public List<MangaDTO> getAllMangas() {
        return mangaRepository.findAll().stream()
                .map(mangaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get Manga by ID and return as MangaDTO
    public MangaDTO getMangaById(Long id) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException(id)); // Throw custom exception
        return mangaMapper.toDTO(manga);
    }

    // Create a new Manga from MangaDTO
    @Transactional
    public MangaDTO createManga(MangaDTO mangaDTO) {
        Manga manga = mangaMapper.fromDTO(mangaDTO);
        manga = mangaRepository.save(manga);
        return mangaMapper.toDTO(manga);
    }

    // Update an existing Manga from MangaDTO
    @Transactional
    public MangaDTO updateManga(Long id, MangaDTO mangaDTO) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException(id)); // Throw custom exception

        // Set updated values from the DTO
        manga.setName(mangaDTO.getName());
        manga.setDateAired(mangaDTO.getDateAired());
        manga.setVol(mangaDTO.getVol());
        manga.setRating(mangaDTO.getRating());

        // Handle chapters and volume logic if needed
        // For example, you could add logic to update volumes or chapters if needed
        // You can also add fields in the DTO and mapper to include chapters per volume

        manga = mangaRepository.save(manga); // Save the updated manga
        return mangaMapper.toDTO(manga); // Return the updated Manga as DTO
    }

    // Delete a Manga by ID
    @Transactional
    public void deleteManga(Long id) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException(id)); // Throw custom exception
        mangaRepository.delete(manga);
    }

    // Additional logic for managing chapters or volumes can be added here if required.
    // For example, a method to manage chapters or volumes individually.
}
