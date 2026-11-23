package ru.cinema.ui.command;

import ru.cinema.domain.entity.Hall;
import ru.cinema.domain.entity.Movie;
import ru.cinema.domain.entity.Screening;
import ru.cinema.domain.entity.Ticket;
import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

import java.util.List;
import java.util.Optional;

abstract class BaseCommand implements Command {

    protected final CinemaService service;
    protected final ConsoleIo io;
    protected final CinemaView view;

    protected BaseCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        this.service = service;
        this.io = io;
        this.view = view;
    }

    protected Optional<Movie> chooseMovie() {
        final List<Movie> movies = service.getAllMovies();
        view.printMovies(movies);
        if (movies.isEmpty()) {
            return Optional.empty();
        }
        return io.choose(movies, "  Номер фильма: ");
    }

    protected Optional<Hall> chooseHall() {
        final List<Hall> halls = service.getAllHalls();
        view.printHalls(halls);
        if (halls.isEmpty()) {
            return Optional.empty();
        }
        return io.choose(halls, "  Номер зала: ");
    }

    protected Optional<Screening> chooseScreening() {
        final List<Screening> screenings = service.getAllScreenings();
        view.printScreenings(screenings);
        if (screenings.isEmpty()) {
            return Optional.empty();
        }
        return io.choose(screenings, "  Номер сеанса: ");
    }

    protected Optional<Ticket> chooseTicket(Screening screening) {
        final List<Ticket> tickets = service.getTicketsOfScreening(screening.getId());
        view.printTickets(tickets);
        if (tickets.isEmpty()) {
            return Optional.empty();
        }
        return io.choose(tickets, "  Номер билета: ");
    }
}
