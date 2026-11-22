package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class SearchMoviesCommand extends BaseCommand {

    public SearchMoviesCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Найти фильм по названию";
    }

    @Override
    public void execute() {
        final String text = io.readNonBlank("  Часть названия: ");
        view.printMovies(service.searchMovies(text));
    }
}
