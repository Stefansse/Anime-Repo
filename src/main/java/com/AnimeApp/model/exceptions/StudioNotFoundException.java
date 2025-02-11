package com.AnimeApp.model.exceptions;

public class StudioNotFoundException extends RuntimeException {

    // Constructor
    public StudioNotFoundException(Long id) {
        super("Studio not found with id " + id);
    }
}
