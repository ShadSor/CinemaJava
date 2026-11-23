package ru.cinema.repository;

import ru.cinema.domain.entity.Movie;

import java.util.List;
import java.util.UUID;

public interface MovieRepository extends MyRepository<Movie, UUID> {

    List<Movie> findByTitleContaining(String text);
}
