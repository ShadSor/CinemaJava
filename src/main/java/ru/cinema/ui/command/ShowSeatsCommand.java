package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class ShowSeatsCommand extends BaseCommand {

    public ShowSeatsCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Схема мест на сеансе";
    }

    @Override
    public void execute() {
        chooseScreening().ifPresent(screening -> {
            io.println();
            view.printSeatMap(screening, service.getTicketsOfScreening(screening.getId()));
        });
    }
}
