package com.flowiee.app.base;

import java.util.List;

public interface BaseService<T> {
    T findById(Integer entityId);

    T save(T entity);

    T update(T entity, Integer entityId);

    String delete(Integer entityId);
}