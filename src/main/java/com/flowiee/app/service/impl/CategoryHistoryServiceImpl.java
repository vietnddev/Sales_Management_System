package com.flowiee.app.service.impl;

import com.flowiee.app.entity.CategoryHistory;
import com.flowiee.app.repository.CategoryHistoryRepository;
import com.flowiee.app.service.CategoryHistoryService;
import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryHistoryServiceImpl implements CategoryHistoryService {
    @Autowired
    private CategoryHistoryRepository categoryHistoryRepo;

    @Override
    public List<CategoryHistory> findAll() {
        return categoryHistoryRepo.findAll();
    }

    @Transactional
    @Override
    public void deleteAllByCategory(Integer categoryId) {
         categoryHistoryRepo.deleteAllByCategory(categoryId);
    }

    @Override
    public CategoryHistory findById(Integer entityId) {
        return categoryHistoryRepo.findById(entityId).orElse(null);
    }

    @Override
    public CategoryHistory save(CategoryHistory entity) {
        return categoryHistoryRepo.save(entity);
    }

    @Override
    public CategoryHistory update(CategoryHistory entity, Integer entityId) {
        entity.setId(entityId);
        return categoryHistoryRepo.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        categoryHistoryRepo.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}