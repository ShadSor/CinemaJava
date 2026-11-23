package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class ListMoviesCommand extends BaseCommand {

    public ListMoviesCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Показать фильмы";
    }

    @Override
    public void execute() {
        view.printMovies(service.getAllMovies());
    }
}
