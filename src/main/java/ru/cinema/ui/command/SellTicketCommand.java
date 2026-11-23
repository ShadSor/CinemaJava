package ru.cinema.ui.command;

import ru.cinema.domain.entity.Screening;
import ru.cinema.domain.entity.Ticket;
import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

import java.util.Optional;

public class SellTicketCommand extends BaseCommand {

    public SellTicketCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Продать билет";
    }

    @Override
    public void execute() {
        final Optional<Screening> chosen = chooseScreening();
        if (chosen.isEmpty()) {
            return;
        }
        final Screening screening = chosen.get();
        io.println();
        view.printSeatMap(screening, service.getTicketsOfScreening(screening.getId()));
        io.println();
        final int row = io.readInt("  Ряд: ");
        final int seat = io.readInt("  Место: ");
        final String customer = io.readNonBlank("  Имя покупателя: ");
        final int age = io.readInt("  Возраст покупателя: ");
        final Ticket ticket = service.sellTicket(screening.getId(), row, seat, customer, age);
        io.println("  Билет продан: " + ticket + ". К оплате: " + view.money(ticket.getPrice()));
    }
}
