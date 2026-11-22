package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class CancelScreeningCommand extends BaseCommand {

    public CancelScreeningCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Отменить сеанс";
    }

    @Override
    public void execute() {
        chooseScreening().ifPresent(screening -> {
            if (io.confirm("  Отменить сеанс «" + screening + "»?")) {
                service.cancelScreening(screening.getId());
                io.println("  Сеанс отменён.");
            } else {
                io.println("  Отмена не выполнена.");
            }
        });
    }
}
