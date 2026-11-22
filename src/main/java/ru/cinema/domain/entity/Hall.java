package ru.cinema.domain.entity;

import ru.cinema.domain.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Hall extends AbstractEntity<UUID> {

    private String name;
    private int rows;
    private int seatsPerRow;

    public Hall(UUID id, String name, int rows, int seatsPerRow) {
        super(id);
        validate(name, rows, seatsPerRow);
        this.name = name.trim();
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }

    public Hall(String name, int rows, int seatsPerRow) {
        this(UUID.randomUUID(), name, rows, seatsPerRow);
    }

    public Hall(UUID uuid, LocalDateTime createdAt, LocalDateTime updatedAt, String name, int rows, int seatsPerRow) {
        super(uuid, createdAt, updatedAt);
        validate(name, rows, seatsPerRow);
        this.name = name.trim();
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }

    private static void validate(String name, int rows, int seatsPerRow) {
        validateName(name);
        validateRows(rows);
        validateSeatsPerRow(seatsPerRow);
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Имя зала не может быть пустым");
        }
    }

    private static void validateRows(int rows) {
        if (rows <= 0) {
            throw new ValidationException("Количество рядов должно быть больше нуля");
        }
    }

    private static void validateSeatsPerRow(int seatsPerRow) {
        if (seatsPerRow <= 0) {
            throw new ValidationException("В ряду должно быть минимум одно место");
        }
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int capacity() {
        return rows * seatsPerRow;
    }

    public boolean hasSeat(int row, int seat) {
        return row >= 1 && row <= rows && seat >= 1 && seat <= seatsPerRow;
    }

    @Override
    public String toString() {
        return String.format("%s, %d мест (%d рядов по %d)", name, capacity(), rows, seatsPerRow);
    }
}
