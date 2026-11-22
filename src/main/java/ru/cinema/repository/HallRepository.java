package ru.cinema.repository;

import ru.cinema.domain.entity.Hall;

import java.util.Optional;
import java.util.UUID;

public interface HallRepository extends MyRepository<Hall, UUID> {

    Optional<Hall> findByName(String name);
}
