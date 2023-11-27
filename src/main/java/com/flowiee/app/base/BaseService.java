package com.flowiee.app.base;

import java.util.List;

public interface BaseService<T> {
    List<T> findAll();

    T findById(Integer entityId);

    String save(T entity);

    String update(T entity, Integer entityId);

    String delete(Integer entityId);
}