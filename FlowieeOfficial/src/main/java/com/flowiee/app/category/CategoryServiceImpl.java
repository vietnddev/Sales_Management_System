package com.flowiee.app.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Integer entityId) {
        return null;
    }

    @Override
    public String save(Category entity) {
        return null;
    }

    @Override
    public String update(Category entity, Integer entityId) {
        return null;
    }

    @Override
    public String delete(Integer entityId) {
        return null;
    }

    @Override
    public List<Category> findByType(String categoryType) {
        List<Category> listData = new ArrayList<>();

        return listData;
    }
}