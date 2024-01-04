package com.flowiee.app.base;

import java.util.List;

public interface BaseService<T> {
    T findById(Integer entityId);

    String save(T entity);

    String update(T entity, Integer entityId);

    String delete(Integer entityId);
}