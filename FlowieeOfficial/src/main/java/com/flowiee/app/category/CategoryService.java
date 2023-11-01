package com.flowiee.app.category;

import com.flowiee.app.base.BaseService;

import java.util.List;

public interface CategoryService extends BaseService<Category> {
    List<Category> findRootCategory();

    List<Category> findSubCategory(String categoryType);
}