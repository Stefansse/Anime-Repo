package com.AnimeApp.model.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long id) {
        super("Author with ID " + id + " not found.");
    }
}
