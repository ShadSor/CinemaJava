package ru.cinema.ui;

import ru.cinema.domain.entity.Hall;
import ru.cinema.domain.entity.Movie;
import ru.cinema.domain.entity.Screening;
import ru.cinema.domain.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;

public class CinemaView {

    private final ConsoleIo io;

    public CinemaView(ConsoleIo io) {
        this.io = io;
    }

    public void printMovies(List<Movie> movies) {
        if (movies.isEmpty()) {
            io.println("  Фильмов пока нет.");
            return;
        }
        io.printTable(
                List.of("Название", "Жанр", "Длительность", "Возраст"),
                movies.stream()
                        .map(m -> List.of(
                                m.getTitle(), m.getGenre(), m.getDurationMinutes() + " мин", m.getAgeLimit() + "+"))
                        .toList());
    }

    public void printHalls(List<Hall> halls) {
        if (halls.isEmpty()) {
            io.println("  Залов пока нет.");
            return;
        }
        io.printTable(
                List.of("Зал", "Рядов", "Мест в ряду", "Всего мест"),
                halls.stream()
                        .map(h -> List.of(
                                h.getName(),
                                String.valueOf(h.getRows()),
                                String.valueOf(h.getSeatsPerRow()),
                                String.valueOf(h.capacity())))
                        .toList());
    }

    public void printScreenings(List<Screening> screenings) {
        if (screenings.isEmpty()) {
            io.println("  Сеансов пока нет.");
            return;
        }
        io.printTable(
                List.of("Фильм", "Зал", "Начало", "Конец", "Цена"),
                screenings.stream()
                        .map(s -> List.of(
                                s.getMovie().getTitle(),
                                s.getHall().getName(),
                                s.getStartTime().format(ConsoleIo.DATE_TIME),
                                s.getEndTime().format(ConsoleIo.DATE_TIME),
                                money(s.getPrice())))
                        .toList());
    }

    public void printTickets(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            io.println("  Билетов нет.");
            return;
        }
        io.printTable(
                List.of("Фильм", "Начало", "Ряд", "Место", "Покупатель", "Цена"),
                tickets.stream()
                        .map(t -> List.of(
                                t.getScreening().getMovie().getTitle(),
                                t.getScreening().getStartTime().format(ConsoleIo.DATE_TIME),
                                String.valueOf(t.getRow()),
                                String.valueOf(t.getSeat()),
                                t.getCustomerName(),
                                money(t.getPrice())))
                        .toList());
    }

    public void printSeatMap(Screening screening, List<Ticket> tickets) {
        final Hall hall = screening.getHall();
        io.println("  " + screening.getMovie().getTitle() + ", " + screening.getStartTime().format(ConsoleIo.DATE_TIME)
                + ", зал «" + hall.getName() + "»");
        io.println();
        final StringBuilder header = new StringBuilder("         ");
        for (int seat = 1; seat <= hall.getSeatsPerRow(); seat++) {
            header.append(String.format("%-3d", seat));
        }
        io.println(header.toString().stripTrailing());
        for (int row = 1; row <= hall.getRows(); row++) {
            final StringBuilder line = new StringBuilder(String.format("  Ряд %-2d ", row));
            for (int seat = 1; seat <= hall.getSeatsPerRow(); seat++) {
                final int currentRow = row;
                final int currentSeat = seat;
                final boolean taken = tickets.stream().anyMatch(t -> t.isForSeat(currentRow, currentSeat));
                line.append(taken ? "[X]" : "[ ]");
            }
            io.println(line.toString());
        }
        io.println();
        final int free = hall.capacity() - tickets.size();
        io.println("  [ ] свободно, [X] занято. Свободных мест: " + free + " из " + hall.capacity());
    }

    public String money(BigDecimal amount) {
        return amount.stripTrailingZeros().toPlainString() + " ₽";
    }
}
