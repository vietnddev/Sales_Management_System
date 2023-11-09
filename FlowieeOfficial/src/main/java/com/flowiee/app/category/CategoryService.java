package com.flowiee.app.category;

import com.flowiee.app.base.BaseService;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CategoryService extends BaseService<Category> {
    List<Category> findRootCategory();

    List<Category> findSubCategory(String categoryType);
    
    String importData(MultipartFile fileImport, String categoryType);

    byte[] exportTemplate(String categoryType);

    byte[] exportData(String categoryType);
}