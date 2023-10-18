package fr.univtln.bruno.demos.demojfx.model;

import lombok.Getter;

@Getter
public class DataAccessException extends Exception {
    private final Exception cause;

    public DataAccessException(String message, Exception cause) {
        super(message);
        this.cause = cause;
    }

    public DataAccessException(Exception cause) {
        super(cause.getLocalizedMessage());
        this.cause = cause;
    }
}
