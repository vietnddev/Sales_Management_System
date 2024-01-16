package com.flowiee.app.base;

public interface BaseService<T> {
    T findById(Integer entityId);

    T save(T entity);

    T update(T entity, Integer entityId);

    String delete(Integer entityId);
}