package ru.cinema.domain.entity;

import ru.cinema.domain.exception.ValidationException;

import java.math.BigDecimal;
import java.util.UUID;

public class Ticket extends AbstractEntity<UUID> {

    private final Screening screening;
    private final int row;
    private final int seat;
    private final String customerName;
    private final BigDecimal price;

    public Ticket(Screening screening, int row, int seat, String customerName) {
        this(UUID.randomUUID(), screening, row, seat, customerName);
    }

    public Ticket(UUID id, Screening screening, int row, int seat, String customerName) {
        super(id);
        if (screening == null) {
            throw new ValidationException("Билет должен относиться к сеансу");
        }
        if (!screening.getHall().hasSeat(row, seat)) {
            throw new ValidationException(
                    String.format("В зале «%s» нет места: ряд %d, место %d", screening.getHall().getName(), row, seat));
        }
        if (customerName == null || customerName.isBlank()) {
            throw new ValidationException("Имя покупателя не может быть пустым");
        }
        this.screening = screening;
        this.row = row;
        this.seat = seat;
        this.customerName = customerName.trim();
        this.price = screening.getPrice();
    }

    public Screening getScreening() {
        return screening;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isForSeat(int row, int seat) {
        return this.row == row && this.seat == seat;
    }

    @Override
    public String toString() {
        return String.format("%s, ряд %d, место %d, %s", screening.getMovie().getTitle(), row, seat, customerName);
    }
}
