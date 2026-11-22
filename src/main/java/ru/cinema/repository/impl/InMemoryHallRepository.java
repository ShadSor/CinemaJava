package ru.cinema.repository.impl;

import ru.cinema.domain.entity.Hall;
import ru.cinema.repository.HallRepository;

import java.util.Optional;

public class InMemoryHallRepository extends InMemoryRepository<Hall> implements HallRepository {

    @Override
    public Optional<Hall> findByName(String name) {
        final String wanted = name.trim();
        return findAllMatching(hall -> hall.getName().equalsIgnoreCase(wanted)).stream().findFirst();
    }
}
