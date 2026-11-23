package ru.cinema.ui.command;

import ru.cinema.domain.entity.Hall;
import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

public class AddHallCommand extends BaseCommand {

    public AddHallCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Добавить зал";
    }

    @Override
    public void execute() {
        final String name = io.readNonBlank("  Название зала: ");
        final int rows = io.readInt("  Количество рядов: ");
        final int seatsPerRow = io.readInt("  Мест в ряду: ");
        final Hall hall = service.addHall(name, rows, seatsPerRow);
        io.println("  Зал добавлен: " + hall);
    }
}
