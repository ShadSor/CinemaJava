package ru.cinema.domain.entity;

import ru.cinema.domain.exception.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Screening extends AbstractEntity<UUID> {

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final Movie movie;
    private final Hall hall;
    private final LocalDateTime startTime;
    private BigDecimal price;

    public Screening(Movie movie, Hall hall, LocalDateTime startTime, BigDecimal price) {
        this(UUID.randomUUID(), movie, hall, startTime, price);
    }

    public Screening(UUID id, Movie movie, Hall hall, LocalDateTime startTime, BigDecimal price) {
        super(id);
        if (movie == null) {
            throw new ValidationException("У сеанса должен быть фильм");
        }
        if (hall == null) {
            throw new ValidationException("У сеанса должен быть зал");
        }
        if (startTime == null) {
            throw new ValidationException("У сеанса должно быть время начала");
        }
        validatePrice(price);
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.price = price;
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null || price.signum() < 0) {
            throw new ValidationException("Цена билета не может быть отрицательной");
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public Hall getHall() {
        return hall;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(movie.getDurationMinutes());
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
        this.markAsUpdated();
    }

    public boolean hasStarted(LocalDateTime now) {
        return !now.isBefore(startTime);
    }

    public boolean overlapsWith(Screening other) {
        if (!hall.equals(other.hall)) {
            return false;
        }
        return startTime.isBefore(other.getEndTime()) && other.startTime.isBefore(getEndTime());
    }

    @Override
    public String toString() {
        return String.format("%s, %s, зал «%s»", movie.getTitle(), startTime.format(DISPLAY_FORMAT), hall.getName());
    }
}
