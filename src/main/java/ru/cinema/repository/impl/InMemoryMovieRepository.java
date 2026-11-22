package ru.cinema.repository.impl;

import ru.cinema.domain.entity.Movie;
import ru.cinema.repository.MovieRepository;

import java.util.List;

public class InMemoryMovieRepository extends InMemoryRepository<Movie> implements MovieRepository {

    @Override
    public List<Movie> findByTitleContaining(String text) {
        final String query = text.trim().toLowerCase();
        return findAllMatching(movie -> movie.getTitle().toLowerCase().contains(query));
    }
}
