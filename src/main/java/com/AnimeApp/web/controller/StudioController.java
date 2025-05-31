package com.AnimeApp.web.controller;

import com.AnimeApp.model.dto.mainDTOs.AnimeDTO;
import com.AnimeApp.model.dto.mainDTOs.StudioDTO;
import com.AnimeApp.model.dto.mainDTOs.StudioMainDTO;
import com.AnimeApp.service.mainE.AnimeService;
import com.AnimeApp.service.mainE.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studios")
@CrossOrigin(origins = "http://localhost:5173")
public class StudioController {

    private final StudioService studioService;
    private final AnimeService animeService;

    @Autowired
    public StudioController(StudioService studioService, AnimeService animeService) {
        this.studioService = studioService;
        this.animeService = animeService;
    }

    // Get all studios
    @GetMapping
    public ResponseEntity<List<StudioMainDTO>> getAllStudios() {
        return ResponseEntity.ok(studioService.getAllStudios());
    }

    // Get a studio by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudioDTO> getStudioById(@PathVariable Long id) {
        try {
            StudioDTO studio = studioService.getStudioById(id);
            return new ResponseEntity<>(studio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Studio not found
        }
    }

    // Create a new studio
    @PostMapping("/add")
    public ResponseEntity<StudioDTO> createStudio(@RequestBody StudioDTO studioDTO) {
        try {
            StudioDTO createdStudio = studioService.createStudio(studioDTO);
            return new ResponseEntity<>(createdStudio, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Handle exception for already existing studio
        }
    }

    // Update an existing studio
    @PutMapping("/edit/{id}")
    public ResponseEntity<StudioDTO> updateStudio(@PathVariable Long id, @RequestBody StudioDTO studioDTO) {
        try {
            StudioDTO updatedStudio = studioService.updateStudio(id, studioDTO);
            return new ResponseEntity<>(updatedStudio, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Studio not found
        }
    }

    // Delete a studio
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        try {
            studioService.deleteStudio(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Studio not found
        }
    }


}
