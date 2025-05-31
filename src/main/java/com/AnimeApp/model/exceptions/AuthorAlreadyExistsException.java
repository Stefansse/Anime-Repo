package com.AnimeApp.model.exceptions;


public class AuthorAlreadyExistsException extends RuntimeException {

    public AuthorAlreadyExistsException() {
        super("User already exists.");
    }

    public AuthorAlreadyExistsException(String message) {
        super(message);
    }

    public AuthorAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
