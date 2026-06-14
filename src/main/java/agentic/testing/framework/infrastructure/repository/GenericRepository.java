package agentic.testing.framework.infrastructure.repository;

import agentic.testing.framework.domain.common.Identifiable;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T extends Identifiable> {
    T save(T entity);

    Optional<T> findById(String id);

    List<T> findAll();

    boolean existsById(String id);
}
