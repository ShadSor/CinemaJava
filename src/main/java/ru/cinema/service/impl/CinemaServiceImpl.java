package ru.cinema.service.impl;

import ru.cinema.domain.entity.Hall;
import ru.cinema.domain.entity.Movie;
import ru.cinema.domain.entity.Screening;
import ru.cinema.domain.entity.Ticket;
import ru.cinema.domain.exception.NotFoundException;
import ru.cinema.domain.exception.ValidationException;
import ru.cinema.repository.HallRepository;
import ru.cinema.repository.MovieRepository;
import ru.cinema.repository.ScreeningRepository;
import ru.cinema.repository.TicketRepository;
import ru.cinema.service.CinemaService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class CinemaServiceImpl implements CinemaService {

    private final MovieRepository movies;
    private final HallRepository halls;
    private final ScreeningRepository screenings;
    private final TicketRepository tickets;
    private final Clock clock;

    public CinemaServiceImpl(
            MovieRepository movies,
            HallRepository halls,
            ScreeningRepository screenings,
            TicketRepository tickets,
            Clock clock) {
        this.movies = movies;
        this.halls = halls;
        this.screenings = screenings;
        this.tickets = tickets;
        this.clock = clock;
    }

    @Override
    public Movie addMovie(String title, int durationMinutes, String genre, int ageLimit) {
        final Movie movie = new Movie(title, durationMinutes, genre, ageLimit);
        movies.save(movie);
        return movie;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movies.findAll().stream()
                .sorted(Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    @Override
    public List<Movie> searchMovies(String text) {
        return movies.findByTitleContaining(text).stream()
                .sorted(Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    @Override
    public void deleteMovie(UUID movieId) {
        final Movie movie = findMovie(movieId);
        if (!screenings.findByMovieId(movieId).isEmpty()) {
            throw new ValidationException(
                    "Нельзя удалить фильм «" + movie.getTitle() + "»: на него есть сеансы. Сначала отмените их");
        }
        movies.deleteById(movieId);
    }

    @Override
    public Hall addHall(String name, int rows, int seatsPerRow) {
        final Hall hall = new Hall(name, rows, seatsPerRow);
        if (halls.findByName(hall.getName()).isPresent()) {
            throw new ValidationException("Зал с названием «" + hall.getName() + "» уже есть");
        }
        halls.save(hall);
        return hall;
    }

    @Override
    public List<Hall> getAllHalls() {
        return halls.findAll().stream()
                .sorted(Comparator.comparing(Hall::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    @Override
    public void deleteHall(UUID hallId) {
        final Hall hall = findHall(hallId);
        if (!screenings.findByHallId(hallId).isEmpty()) {
            throw new ValidationException(
                    "Нельзя удалить зал «" + hall.getName() + "»: в нём запланированы сеансы. Сначала отмените их");
        }
        halls.deleteById(hallId);
    }

    @Override
    public Screening scheduleScreening(UUID movieId, UUID hallId, LocalDateTime startTime, BigDecimal price) {
        final Screening screening = new Screening(findMovie(movieId), findHall(hallId), startTime, price);
        if (screening.hasStarted(now())) {
            throw new ValidationException("Нельзя назначить сеанс на время, которое уже прошло");
        }
        screenings.findByHallId(hallId).stream()
                .filter(screening::overlapsWith)
                .findFirst()
                .ifPresent(existing -> {
                    throw new ValidationException(String.format(
                            "В зале «%s» в это время идёт «%s» (%s – %s)",
                            existing.getHall().getName(),
                            existing.getMovie().getTitle(),
                            existing.getStartTime().toLocalTime(),
                            existing.getEndTime().toLocalTime()));
                });
        screenings.save(screening);
        return screening;
    }

    @Override
    public List<Screening> getAllScreenings() {
        return screenings.findAll().stream()
                .sorted(Comparator.comparing(Screening::getStartTime))
                .toList();
    }

    @Override
    public void cancelScreening(UUID screeningId) {
        findScreening(screeningId);
        if (!tickets.findByScreeningId(screeningId).isEmpty()) {
            throw new ValidationException("Нельзя отменить сеанс: на него уже проданы билеты. Сначала верните их");
        }
        screenings.deleteById(screeningId);
    }

    @Override
    public Ticket sellTicket(UUID screeningId, int row, int seat, String customerName, int customerAge) {
        final Screening screening = findScreening(screeningId);
        final Movie movie = screening.getMovie();
        if (screening.hasStarted(now())) {
            throw new ValidationException("Сеанс уже начался, продажа билетов закрыта");
        }
        if (!movie.isAllowedFor(customerAge)) {
            throw new ValidationException(String.format(
                    "Фильм «%s» с ограничением %d+, продать билет покупателю %d лет нельзя",
                    movie.getTitle(), movie.getAgeLimit(), customerAge));
        }
        final Ticket ticket = new Ticket(screening, row, seat, customerName);
        final boolean taken = tickets.findByScreeningId(screeningId).stream().anyMatch(t -> t.isForSeat(row, seat));
        if (taken) {
            throw new ValidationException(String.format("Место %d в ряду %d уже занято", seat, row));
        }
        tickets.save(ticket);
        return ticket;
    }

    @Override
    public void refundTicket(UUID ticketId) {
        final Ticket ticket = tickets.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Билет не найден"));
        if (ticket.getScreening().hasStarted(now())) {
            throw new ValidationException("Сеанс уже начался, вернуть билет нельзя");
        }
        tickets.deleteById(ticketId);
    }

    @Override
    public List<Ticket> getTicketsOfScreening(UUID screeningId) {
        findScreening(screeningId);
        return tickets.findByScreeningId(screeningId).stream()
                .sorted(Comparator.comparingInt(Ticket::getRow).thenComparingInt(Ticket::getSeat))
                .toList();
    }

    @Override
    public List<Ticket> searchTicketsByCustomer(String name) {
        return tickets.findByCustomerName(name);
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    private Movie findMovie(UUID id) {
        return movies.findById(id).orElseThrow(() -> new NotFoundException("Фильм не найден"));
    }

    private Hall findHall(UUID id) {
        return halls.findById(id).orElseThrow(() -> new NotFoundException("Зал не найден"));
    }

    private Screening findScreening(UUID id) {
        return screenings.findById(id).orElseThrow(() -> new NotFoundException("Сеанс не найден"));
    }
}
