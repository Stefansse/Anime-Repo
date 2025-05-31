package com.AnimeApp.model.exceptions;

public class MangaNotFoundException extends RuntimeException {

    public MangaNotFoundException(Long id) {
        super("Manga with ID " + id + " not found.");
    }
}
