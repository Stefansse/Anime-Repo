package com.AnimeApp.model.exceptions;

public class StudioNotFoundException extends RuntimeException {

    public StudioNotFoundException(Long id) {
        super("Studio with ID " + id + " not found.");
    }
}