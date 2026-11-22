package ru.cinema;

import ru.cinema.repository.HallRepository;
import ru.cinema.repository.MovieRepository;
import ru.cinema.repository.ScreeningRepository;
import ru.cinema.repository.TicketRepository;
import ru.cinema.repository.impl.InMemoryHallRepository;
import ru.cinema.repository.impl.InMemoryMovieRepository;
import ru.cinema.repository.impl.InMemoryScreeningRepository;
import ru.cinema.repository.impl.InMemoryTicketRepository;
import ru.cinema.service.CinemaService;
import ru.cinema.service.impl.CinemaServiceImpl;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;
import ru.cinema.ui.Menu;
import ru.cinema.ui.MenuSection;
import ru.cinema.ui.command.AddHallCommand;
import ru.cinema.ui.command.AddMovieCommand;
import ru.cinema.ui.command.CancelScreeningCommand;
import ru.cinema.ui.command.DeleteHallCommand;
import ru.cinema.ui.command.DeleteMovieCommand;
import ru.cinema.ui.command.ListHallsCommand;
import ru.cinema.ui.command.ListMoviesCommand;
import ru.cinema.ui.command.ListScreeningsCommand;
import ru.cinema.ui.command.ListTicketsCommand;
import ru.cinema.ui.command.RefundTicketCommand;
import ru.cinema.ui.command.ScheduleScreeningCommand;
import ru.cinema.ui.command.SearchMoviesCommand;
import ru.cinema.ui.command.SearchTicketsCommand;
import ru.cinema.ui.command.SellTicketCommand;
import ru.cinema.ui.command.ShowSeatsCommand;

import java.time.Clock;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Clock clock = Clock.systemDefaultZone();
        final MovieRepository movies = new InMemoryMovieRepository();
        final HallRepository halls = new InMemoryHallRepository();
        final ScreeningRepository screenings = new InMemoryScreeningRepository();
        final TicketRepository tickets = new InMemoryTicketRepository();
        final CinemaService service = new CinemaServiceImpl(movies, halls, screenings, tickets, clock);

        if (!hasFlag(args, "--empty")) {
            DemoData.fill(service, clock);
        }

        final ConsoleIo io = new ConsoleIo(new Scanner(System.in));
        final CinemaView view = new CinemaView(io);
        new Menu(io, buildSections(service, io, view)).run();
    }

    private static List<MenuSection> buildSections(CinemaService service, ConsoleIo io, CinemaView view) {
        return List.of(
                new MenuSection("Фильмы", List.of(
                        new ListMoviesCommand(service, io, view),
                        new AddMovieCommand(service, io, view),
                        new SearchMoviesCommand(service, io, view),
                        new DeleteMovieCommand(service, io, view))),
                new MenuSection("Залы", List.of(
                        new ListHallsCommand(service, io, view),
                        new AddHallCommand(service, io, view),
                        new DeleteHallCommand(service, io, view))),
                new MenuSection("Сеансы", List.of(
                        new ListScreeningsCommand(service, io, view),
                        new ScheduleScreeningCommand(service, io, view),
                        new CancelScreeningCommand(service, io, view),
                        new ShowSeatsCommand(service, io, view))),
                new MenuSection("Билеты", List.of(
                        new SellTicketCommand(service, io, view),
                        new RefundTicketCommand(service, io, view),
                        new ListTicketsCommand(service, io, view),
                        new SearchTicketsCommand(service, io, view))));
    }

    private static boolean hasFlag(String[] args, String flag) {
        for (String arg : args) {
            if (arg.equals(flag)) {
                return true;
            }
        }
        return false;
    }
}
