package com.flowiee.pms.service.category.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.category.CategoryHistory;
import com.flowiee.pms.repository.category.CategoryHistoryRepository;
import com.flowiee.pms.service.category.CategoryHistoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CategoryHistoryServiceImpl implements CategoryHistoryService {
    @Autowired
    private CategoryHistoryRepository categoryHistoryRepository;

    @Override
    public List<CategoryHistory> save(Map<String, Object[]> logChanges, String title, Integer categoryId) {
        List<CategoryHistory> categoryHistories = new ArrayList<>();
        for (Map.Entry<String, Object[]> entry : logChanges.entrySet()) {
            String field = entry.getKey();
            String oldValue = ObjectUtils.isNotEmpty(entry.getValue()[0]) ? entry.getValue()[0].toString() : " ";
            String newValue = ObjectUtils.isNotEmpty(entry.getValue()[1]) ? entry.getValue()[1].toString() : " ";
            CategoryHistory categoryHistory = new CategoryHistory();
            categoryHistory.setTitle(title);
            categoryHistory.setCategory(new Category(categoryId, null));
            categoryHistory.setField(field);
            categoryHistory.setOldValue(oldValue);
            categoryHistory.setNewValue(newValue);
            categoryHistories.add(categoryHistoryRepository.save(categoryHistory));
        }
        return categoryHistories;
    }
}