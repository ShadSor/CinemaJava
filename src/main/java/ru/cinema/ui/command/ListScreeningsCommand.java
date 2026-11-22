package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class ListScreeningsCommand extends BaseCommand {

    public ListScreeningsCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Показать сеансы";
    }

    @Override
    public void execute() {
        view.printScreenings(service.getAllScreenings());
    }
}
