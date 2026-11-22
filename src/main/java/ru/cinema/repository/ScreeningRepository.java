package ru.cinema.repository;

import ru.cinema.domain.entity.Screening;

import java.util.List;
import java.util.UUID;

public interface ScreeningRepository extends MyRepository<Screening, UUID> {

    List<Screening> findByMovieId(UUID movieId);

    List<Screening> findByHallId(UUID hallId);
}
