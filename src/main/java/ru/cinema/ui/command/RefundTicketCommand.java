package ru.cinema.ui.command;

import ru.cinema.domain.entity.Screening;
import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

import java.util.Optional;

public class RefundTicketCommand extends BaseCommand {

    public RefundTicketCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Вернуть билет";
    }

    @Override
    public void execute() {
        final Optional<Screening> screening = chooseScreening();
        if (screening.isEmpty()) {
            return;
        }
        io.println();
        chooseTicket(screening.get()).ifPresent(ticket -> {
            if (io.confirm("  Вернуть билет «" + ticket + "»?")) {
                service.refundTicket(ticket.getId());
                io.println("  Билет возвращён, к возврату: " + view.money(ticket.getPrice()));
            } else {
                io.println("  Возврат отменён.");
            }
        });
    }
}
