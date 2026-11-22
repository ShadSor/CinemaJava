package ru.cinema.repository;

import ru.cinema.domain.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends MyRepository<Ticket, UUID> {

    List<Ticket> findByScreeningId(UUID screeningId);

    List<Ticket> findByCustomerName(String name);
}
