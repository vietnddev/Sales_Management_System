package com.flowiee.pms.service.category;

import com.flowiee.pms.service.BaseCurdService;

import java.util.List;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.utils.constants.CategoryType;
import org.springframework.data.domain.Page;

public interface CategoryService extends BaseCurdService<Category> {
    List<Category> findRootCategory();

    Page<Category> findSubCategory(CategoryType categoryType, Integer parentId, List<Integer> ignoreIds, int pageSize, int pageNum);

    List<Category> findUnits();

    List<Category> findColors();

    List<Category> findSizes();

    List<Category> findSalesChannels();

    List<Category> findPaymentMethods();

    List<Category> findOrderStatus(Integer ignoreId);

    List<Category> findLedgerGroupObjects();

    List<Category> findLedgerReceiptTypes();

    List<Category> findLedgerPaymentTypes();

    boolean categoryInUse(Integer categoryId);
}