package ru.cinema.ui.command;

import ru.cinema.domain.entity.Hall;
import ru.cinema.domain.entity.Movie;
import ru.cinema.domain.entity.Screening;
import ru.cinema.service.CinemaService;
import ru.cinema.ui.CinemaView;
import ru.cinema.ui.ConsoleIo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class ScheduleScreeningCommand extends BaseCommand {

    public ScheduleScreeningCommand(CinemaService service, ConsoleIo io, CinemaView view) {
        super(service, io, view);
    }

    @Override
    public String title() {
        return "Назначить сеанс";
    }

    @Override
    public void execute() {
        io.println("  Выберите фильм:");
        final Optional<Movie> movie = chooseMovie();
        if (movie.isEmpty()) {
            return;
        }
        io.println();
        io.println("  Выберите зал:");
        final Optional<Hall> hall = chooseHall();
        if (hall.isEmpty()) {
            return;
        }
        io.println();
        final LocalDateTime start = io.readDateTime("  Начало (дд.мм.гггг чч:мм): ");
        final BigDecimal price = io.readMoney("  Цена билета, ₽: ");
        final Screening screening = service.scheduleScreening(movie.get().getId(), hall.get().getId(), start, price);
        io.println("  Сеанс назначен: " + screening.getMovie().getTitle() + ", "
                + screening.getStartTime().format(ConsoleIo.DATE_TIME) + " – "
                + screening.getEndTime().format(ConsoleIo.DATE_TIME));
    }
}
