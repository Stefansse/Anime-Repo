package com.AnimeApp.web.controller;

import com.AnimeApp.model.dto.mainDTOs.MangaDTO;
import com.AnimeApp.service.mainE.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mangas")
@CrossOrigin(origins = "http://localhost:5173")
public class MangaController {

    private final MangaService mangaService;

    @Autowired
    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    // Get all mangas
    @GetMapping
    public ResponseEntity<List<MangaDTO>> getAllMangas() {
        List<MangaDTO> mangas = mangaService.getAllMangas();
        return new ResponseEntity<>(mangas, HttpStatus.OK);
    }

    // Get a manga by ID
    @GetMapping("/{id}")
    public ResponseEntity<MangaDTO> getMangaById(@PathVariable Long id) {
        try {
            MangaDTO manga = mangaService.getMangaById(id);
            return new ResponseEntity<>(manga, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Manga not found
        }
    }

    // Create a new manga
    @PostMapping("/add")
    public ResponseEntity<MangaDTO> createManga(@RequestBody MangaDTO mangaDTO) {
        try {
            MangaDTO createdManga = mangaService.createManga(mangaDTO);
            return new ResponseEntity<>(createdManga, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle exception
        }
    }

    // Update an existing manga
    @PutMapping("/edit/{id}")
    public ResponseEntity<MangaDTO> updateManga(@PathVariable Long id, @RequestBody MangaDTO mangaDTO) {
        try {
            MangaDTO updatedManga = mangaService.updateManga(id, mangaDTO);
            return new ResponseEntity<>(updatedManga, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Manga not found
        }
    }

    // Delete a manga
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteManga(@PathVariable Long id) {
        try {
            mangaService.deleteManga(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Manga not found
        }
    }
}
