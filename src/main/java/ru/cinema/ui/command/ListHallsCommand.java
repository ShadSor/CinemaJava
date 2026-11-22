package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class ListHallsCommand extends BaseCommand {

    public ListHallsCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Показать залы";
    }

    @Override
    public void execute() {
        view.printHalls(service.getAllHalls());
    }
}
