package ru.cinema.ui.command;

import ru.cinema.domain.entity.Movie;
import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class AddMovieCommand extends BaseCommand {

    public AddMovieCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Добавить фильм";
    }

    @Override
    public void execute() {
        final String title = io.readNonBlank("  Название: ");
        final String genre = io.readNonBlank("  Жанр: ");
        final int duration = io.readInt("  Длительность, минут: ");
        final int ageLimit = io.readInt("  Возрастное ограничение (0, 6, 12, 16, 18): ");
        final Movie movie = service.addMovie(title, duration, genre, ageLimit);
        io.println("  Фильм добавлен: " + movie);
    }
}
