package ru.cinema.repository.impl;

import ru.cinema.domain.entity.AbstractEntity;
import ru.cinema.repository.MyRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public abstract class InMemoryRepository<E extends AbstractEntity<UUID>> implements MyRepository<E, UUID> {

    private final Map<UUID, E> storage = new LinkedHashMap<>();

    @Override
    public void save(E entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }

    @Override
    public Optional<E> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<E> findAll() {
        return List.copyOf(storage.values());
    }

    protected List<E> findAllMatching(Predicate<E> condition) {
        return storage.values().stream().filter(condition).toList();
    }
}
