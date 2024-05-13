package com.flowiee.pms.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {
    List<T> findAll();

    Optional<T> findById(Integer entityId);

    T save(T entity);

    T update(T entity, Integer entityId);

    String delete(Integer entityId);
}