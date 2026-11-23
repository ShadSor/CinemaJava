package ru.cinema;

import ru.cinema.domain.entity.Hall;
import ru.cinema.domain.entity.Movie;
import ru.cinema.domain.entity.Screening;
import ru.cinema.service.CinemaService;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DemoData {

    private DemoData() {
    }

    public static void fill(CinemaService service, Clock clock) {
        final Movie interstellar = service.addMovie("Интерстеллар", 169, "Фантастика", 12);
        final Movie brother = service.addMovie("Брат", 100, "Драма", 16);
        final Movie cheburashka = service.addMovie("Чебурашка", 113, "Семейный", 0);
        service.addMovie("Остров проклятых", 138, "Триллер", 18);

        final Hall red = service.addHall("Красный", 6, 10);
        final Hall blue = service.addHall("Синий", 4, 8);

        final LocalDate tomorrow = LocalDate.now(clock).plusDays(1);
        final Screening first = service.scheduleScreening(
                interstellar.getId(), red.getId(), at(tomorrow, 12, 0), new BigDecimal("450"));
        service.scheduleScreening(brother.getId(), red.getId(), at(tomorrow, 19, 30), new BigDecimal("400"));
        service.scheduleScreening(cheburashka.getId(), blue.getId(), at(tomorrow, 11, 0), new BigDecimal("300"));

        service.sellTicket(first.getId(), 3, 5, "Иван Петров", 25);
        service.sellTicket(first.getId(), 3, 6, "Мария Петрова", 24);
    }

    private static LocalDateTime at(LocalDate date, int hour, int minute) {
        return LocalDateTime.of(date, LocalTime.of(hour, minute));
    }
}
