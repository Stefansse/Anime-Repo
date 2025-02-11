package com.AnimeApp.model.exceptions;

public class AnimeNotFoundException extends RuntimeException {

    // Constructor
    public AnimeNotFoundException(Long id) {
        super("Anime not found with id " + id);
    }
}