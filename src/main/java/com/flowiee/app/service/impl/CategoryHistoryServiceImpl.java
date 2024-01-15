package com.flowiee.app.service.impl;

import com.flowiee.app.entity.CategoryHistory;
import com.flowiee.app.repository.CategoryHistoryRepository;
import com.flowiee.app.service.CategoryHistoryService;
import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryHistoryServiceImpl implements CategoryHistoryService {
    @Autowired
    private CategoryHistoryRepository categoryHistoryRepository;

    @Override
    public List<CategoryHistory> findAll() {
        return categoryHistoryRepository.findAll();
    }

    @Override
    public CategoryHistory findById(Integer entityId) {
        return categoryHistoryRepository.findById(entityId).orElse(null);
    }

    @Override
    public CategoryHistory save(CategoryHistory entity) {
        return categoryHistoryRepository.save(entity);
    }

    @Override
    public CategoryHistory update(CategoryHistory entity, Integer entityId) {
        entity.setId(entityId);
        return categoryHistoryRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        categoryHistoryRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}