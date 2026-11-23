package ru.cinema.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class AbstractEntity<ID> {
    private final ID id;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected AbstractEntity(ID id) {
        this(id, LocalDateTime.now(), LocalDateTime.now());
    }

    protected AbstractEntity(ID id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    public ID getId() {
        return this.id;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void markAsUpdated() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractEntity<?> other)) {
            return false;
        }
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
