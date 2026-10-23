package ru.cinema.domain.exception;

public class NotFoundException extends CinemaException{
    public NotFoundException(String message) {
        super(message);
    }
}
