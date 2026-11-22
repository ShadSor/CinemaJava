package ru.cinema.ui.command;

import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class DeleteHallCommand extends BaseCommand {

    public DeleteHallCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Удалить зал";
    }

    @Override
    public void execute() {
        chooseHall().ifPresent(hall -> {
            if (io.confirm("  Удалить зал «" + hall.getName() + "»?")) {
                service.deleteHall(hall.getId());
                io.println("  Зал удалён.");
            } else {
                io.println("  Удаление отменено.");
            }
        });
    }
}
