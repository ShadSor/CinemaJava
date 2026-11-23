package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class DeleteMovieCommand extends BaseCommand {

    public DeleteMovieCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Удалить фильм";
    }

    @Override
    public void execute() {
        chooseMovie().ifPresent(movie -> {
            if (io.confirm("  Удалить фильм «" + movie.getTitle() + "»?")) {
                service.deleteMovie(movie.getId());
                io.println("  Фильм удалён.");
            } else {
                io.println("  Удаление отменено.");
            }
        });
    }
}
