package com.flowiee.pms.service.category;

import com.flowiee.pms.service.BaseCurdService;

import java.util.List;

import com.flowiee.pms.entity.category.Category;
import org.springframework.data.domain.Page;

public interface CategoryService extends BaseCurdService<Category> {
    List<Category> findRootCategory();

    List<Category> findSubCategory(String categoryType, Integer parentId);

    Page<Category> findSubCategory(String categoryType, Integer parentId, int pageSize, int pageNum);

    List<Category> findUnits();

    List<Category> findColors();

    List<Category> findSizes();

    List<Category> findSalesChannels();

    List<Category> findPaymentMethods();

    List<Category> findOrderStatus(Integer ignoreId);

    List<Category> findLedgerGroupObjects();

    List<Category> findLedgerReceiptTypes();

    List<Category> findLedgerPaymentTypes();

    Boolean categoryInUse(Integer categoryId);
}