package ru.cinema.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Movie extends AbstractEntity<UUID> {

    private String title;
    private int durationMinutes;
    private String genre;
    private int ageLimit;

    public Movie(String title, int durationMinutes, String genre, int ageLimit) {
        this(UUID.randomUUID(), title, durationMinutes, genre, ageLimit);
    }

    public Movie(UUID id, String title, int durationMinutes, String genre, int ageLimit) {
        super(id);
        validate(title, durationMinutes, genre, ageLimit);
        this.title = title.trim();
        this.durationMinutes = durationMinutes;
        this.genre = genre.trim();
        this.ageLimit = ageLimit;
    }

    public Movie(
            UUID id,
            String title,
            int durationMinutes,
            String genre,
            int ageLimit,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        validate(title, durationMinutes, genre, ageLimit);
        this.title = title.trim();
        this.durationMinutes = durationMinutes;
        this.genre = genre.trim();
        this.ageLimit = ageLimit;
    }

    private static void validate(String title, int durationMinutes, String genre, int ageLimit) {
        validateTitle(title);
        validateDurationMinutes(durationMinutes);
        validateGenre(genre);
        validateAgeLimit(ageLimit);
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название фильма не может быть пустым");
        }
    }

    private static void validateDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Длительность фильма должна быть больше нуля");
        }
    }

    private static void validateGenre(String genre) {
        if (genre == null || genre.isBlank()) {
            throw new IllegalArgumentException("У фильма должен быть жанр");
        }
    }

    private static void validateAgeLimit(int ageLimit) {
        if (ageLimit < 0) {
            throw new IllegalArgumentException("Ограничение по возрасту не может быть отрицательным");
        }
    }

    public String getTitle() {
        return title;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title.trim();
        this.markAsUpdated();
    }

    public void setDurationMinutes(int durationMinutes) {
        validateDurationMinutes(durationMinutes);
        this.durationMinutes = durationMinutes;
        this.markAsUpdated();
    }

    public void setGenre(String genre) {
        validateGenre(genre);
        this.genre = genre.trim();
        this.markAsUpdated();
    }

    public void setAgeLimit(int ageLimit) {
        validateAgeLimit(ageLimit);
        this.ageLimit = ageLimit;
        this.markAsUpdated();
    }

    @Override
    public String toString() {
        return String.format("%s, %d мин, %s, %d+", title, durationMinutes, genre, ageLimit);
    }
}
