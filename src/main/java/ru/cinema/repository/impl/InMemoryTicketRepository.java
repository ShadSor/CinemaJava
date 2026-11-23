package ru.cinema.repository.impl;

import ru.cinema.domain.entity.Ticket;
import ru.cinema.repository.TicketRepository;

import java.util.List;
import java.util.UUID;

public class InMemoryTicketRepository extends InMemoryRepository<Ticket> implements TicketRepository {

    @Override
    public List<Ticket> findByScreeningId(UUID screeningId) {
        return findAllMatching(ticket -> ticket.getScreening().getId().equals(screeningId));
    }

    @Override
    public List<Ticket> findByCustomerName(String name) {
        final String query = name.trim().toLowerCase();
        return findAllMatching(ticket -> ticket.getCustomerName().toLowerCase().contains(query));
    }
}
