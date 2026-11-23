package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class SearchTicketsCommand extends BaseCommand {

    public SearchTicketsCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Найти билеты по имени покупателя";
    }

    @Override
    public void execute() {
        final String name = io.readNonBlank("  Имя покупателя: ");
        view.printTickets(service.searchTicketsByCustomer(name));
    }
}
