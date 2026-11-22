package ru.cinema.repository;

import ru.cinema.domain.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface MyRepository<E extends AbstractEntity<ID>, ID> {

    void save(E entity);

    void deleteById(ID id);

    Optional<E> findById(ID id);

    List<E> findAll();
}
