package com.flowiee.app.category;

import com.flowiee.app.common.utils.CategoryUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.product.services.ProductService;
import com.flowiee.app.storage.entity.Document;
import com.flowiee.app.storage.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private DocumentService documentService;

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

    private Boolean categoryInUse(Integer categoryId) {
        Category category = this.findById(categoryId);
        if (category.getType().equals(CategoryUtil.UNIT)) {

        } else if (category.getType().equals(CategoryUtil.FABRICTYPE)) {

        } else if (category.getType().equals(CategoryUtil.PAYMETHOD)) {

        } else if (category.getType().equals(CategoryUtil.SALESCHANNEL)) {

        } else if (category.getType().equals(CategoryUtil.SIZE)) {

        } else if (category.getType().equals(CategoryUtil.COLOR)) {

        } else if (category.getType().equals(CategoryUtil.PRODUCTTYPE)) {

        } else if (category.getType().equals(CategoryUtil.DOCUMENTTYPE)) {
            if (!documentService.findByDoctype(categoryId).isEmpty()) return true;
        } else if (category.getType().equals(CategoryUtil.ORDERSTATUS)) {

        }
        return false;
    }
}