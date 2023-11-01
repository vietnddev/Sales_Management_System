package com.flowiee.app.category;

import com.flowiee.app.common.utils.TagName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return categoryRepository.findById(entityId).orElse(null);
    }

    @Override
    public String save(Category entity) {
        if (entity == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        categoryRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Transactional
    @Override
    public String update(Category entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        categoryRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Transactional
    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0 || this.findById(entityId) == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        categoryRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<Category> findRootCategory() {
        return categoryRepository.findRootCategory();
    }

    @Override
    public List<Category> findSubCategory(String categoryType) {
        return categoryRepository.findSubCategory(categoryType);
    }

    private Boolean checkCategoryInUse(Integer categoryId) {
        Category category = this.findById(categoryId);
        switch (category.getType()) {
            case "UNIT":
                //to do something
                break;
            case "FABRICTYPE":
                //to do something
                break;
            case "PAYMETHOD":
                //to do something
                break;
            case "SALESCHANNEL":
                //to do something
                break;
            case "SIZE":
                // to do something
                break;
            case "COLOR":
                // to do something
                break;
            case "PRODUCTTYPE":
                // to do something
                break;
            case "DOCUMENTTYPE":
                // to do something
                break;
            case "ORDERSTATUS":
                // to do something
                break;
        }

        return true;
    }
}