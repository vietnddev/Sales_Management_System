package com.flowiee.app.services;

import com.flowiee.app.model.category.Category;
import com.flowiee.app.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getListRootCategory(String code, int type){
        return categoryRepository.findByCodeAndType(code, type);
    }

    public List<Category> getListCategory(String code, int type){
        return categoryRepository.findByCodeAndType(code, type);
    }
}
