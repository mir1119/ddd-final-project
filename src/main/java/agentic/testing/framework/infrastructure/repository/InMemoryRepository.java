package agentic.testing.framework.infrastructure.repository;

import agentic.testing.framework.domain.common.DomainValidationException;
import agentic.testing.framework.domain.common.Identifiable;
import agentic.testing.framework.domain.common.ValidationUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Simple in-memory persistence for a runnable final-project prototype.
 * It can be replaced later by JDBC/JPA without changing domain model classes.
 */
public class InMemoryRepository<T extends Identifiable> implements GenericRepository<T> {
    private final Map<String, T> storage = new LinkedHashMap<>();

    @Override
    public T save(T entity) {
        ValidationUtils.requireNotNull(entity, "entity");
        if (entity.getId() == null || entity.getId().trim().isEmpty()) {
            throw new DomainValidationException("Entity id must not be blank.");
        }
        storage.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean existsById(String id) {
        return storage.containsKey(id);
    }
}
