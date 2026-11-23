package ru.cinema.repository.impl;

import ru.cinema.domain.entity.Screening;
import ru.cinema.repository.ScreeningRepository;

import java.util.List;
import java.util.UUID;

public class InMemoryScreeningRepository extends InMemoryRepository<Screening> implements ScreeningRepository {

    @Override
    public List<Screening> findByMovieId(UUID movieId) {
        return findAllMatching(screening -> screening.getMovie().getId().equals(movieId));
    }

    @Override
    public List<Screening> findByHallId(UUID hallId) {
        return findAllMatching(screening -> screening.getHall().getId().equals(hallId));
    }
}
