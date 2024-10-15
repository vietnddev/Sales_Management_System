package com.flowiee.pms.service;

import java.util.List;
import java.util.Optional;

public interface BaseCurdService<T> {
    List<T> findAll();

    Optional<T> findById(Long entityId);

    T save(T entity);

    T update(T entity, Long entityId);

    String delete(Long entityId);
}