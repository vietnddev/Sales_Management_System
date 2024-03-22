package com.flowiee.pms.service.category;

import com.flowiee.pms.base.BaseService;

import java.util.List;

import com.flowiee.pms.entity.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService extends BaseService<Category> {
    List<Category> findRootCategory();

    List<Category> findSubCategory(String categoryType, Integer parentId);

    Page<Category> findSubCategory(String categoryType, Integer parentId, int pageSize, int pageNum);

    Boolean categoryInUse(Integer categoryId);
    
    String importData(MultipartFile fileImport, String categoryType);

    byte[] exportTemplate(String categoryType);

    byte[] exportData(String categoryType);
}