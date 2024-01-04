package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.CategoryHistory;

import java.util.List;

public interface CategoryHistoryService extends BaseService<CategoryHistory> {
    List<CategoryHistory> findAll();
}