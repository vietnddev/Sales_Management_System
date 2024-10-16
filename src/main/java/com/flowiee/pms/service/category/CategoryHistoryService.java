package com.flowiee.pms.service.category;

import com.flowiee.pms.entity.category.CategoryHistory;

import java.util.List;
import java.util.Map;

public interface CategoryHistoryService {
    List<CategoryHistory> save(Map<String, Object[]> logChanges, String title, Long categoryId);
}