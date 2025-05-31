package com.AnimeApp.web.controller;

import com.AnimeApp.model.dto.mainDTOs.AnimeDTO;
import com.AnimeApp.model.enumerations.Genre;
import com.AnimeApp.service.mainE.AnimeService;
import com.AnimeApp.service.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/animes")
@CrossOrigin(origins = "http://localhost:5173")
public class AnimeController {

    private final AnimeService animeService;

    @Autowired
    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    // Get all animes
    @GetMapping
    public ResponseEntity<List<AnimeDTO>> getAllAnimes() {
        List<AnimeDTO> animes = animeService.getAllAnime();
        return new ResponseEntity<>(animes, HttpStatus.OK);
    }

    // Get an anime by ID
    @GetMapping("/{id}")
    public ResponseEntity<AnimeDTO> getAnimeById(@PathVariable Long id) {
        try {
            AnimeDTO anime = animeService.getAnimeById(id);
            return new ResponseEntity<>(anime, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Anime not found
        }
    }

    // Create a new anime
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnimeDTO> createAnime(
            @RequestPart("anime") AnimeDTO animeDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            animeDTO.setImageFile(imageFile);
            AnimeDTO createdAnime = animeService.createAnime(animeDTO);
            return new ResponseEntity<>(createdAnime, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    // Update an existing anime
    @PutMapping("/edit/{id}")
    public ResponseEntity<AnimeDTO> updateAnime(@PathVariable Long id, @RequestBody AnimeDTO animeDTO) {
        try {
            AnimeDTO updatedAnime = animeService.updateAnime(id, animeDTO);
            return new ResponseEntity<>(updatedAnime, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Anime not found
        }
    }

    // Delete an anime
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        try {
            animeService.deleteAnime(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Anime not found
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<AnimeDTO>> filterAnimes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false) Long studioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<AnimeDTO> resultPage = animeService.searchAnimeInElastic(name, genre, type, minRating, maxRating, studioId, page, size);
        return new ResponseEntity<>(resultPage, HttpStatus.OK);
    }

    @GetMapping("/genres")
    public List<String> getGenres() {
        return Arrays.stream(Genre.values())
                .map(Enum::name)
                .map(StringUtils::prettifyGenreName)
                .collect(Collectors.toList());
    }
}
