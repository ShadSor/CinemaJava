package ru.cinema.service;

import ru.cinema.domain.entity.Hall;
import ru.cinema.domain.entity.Movie;
import ru.cinema.domain.entity.Screening;
import ru.cinema.domain.entity.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CinemaService {

    Movie addMovie(String title, int durationMinutes, String genre, int ageLimit);

    List<Movie> getAllMovies();

    List<Movie> searchMovies(String text);

    void deleteMovie(UUID movieId);

    Hall addHall(String name, int rows, int seatsPerRow);

    List<Hall> getAllHalls();

    void deleteHall(UUID hallId);

    Screening scheduleScreening(UUID movieId, UUID hallId, LocalDateTime startTime, BigDecimal price);

    List<Screening> getAllScreenings();

    void cancelScreening(UUID screeningId);

    Ticket sellTicket(UUID screeningId, int row, int seat, String customerName, int customerAge);

    void refundTicket(UUID ticketId);

    List<Ticket> getTicketsOfScreening(UUID screeningId);

    List<Ticket> searchTicketsByCustomer(String name);
}
