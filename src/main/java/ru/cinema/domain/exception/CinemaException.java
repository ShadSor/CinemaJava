package ru.cinema.domain.exception;

public class CinemaException extends RuntimeException {
    protected CinemaException(String message) {
        super(message);
    }

    protected CinemaException(String message, Throwable cause) {
        super(message, cause);
    }
}
