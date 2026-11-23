package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class ListTicketsCommand extends BaseCommand {

    public ListTicketsCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Билеты на сеанс";
    }

    @Override
    public void execute() {
        chooseScreening().ifPresent(screening -> {
            io.println();
            view.printTickets(service.getTicketsOfScreening(screening.getId()));
        });
    }
}
