package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.ProductHistory;

import java.util.List;

public interface ProductHistoryService extends BaseService<ProductHistory> {
    List<ProductHistory> findAll();

    List<ProductHistory> findByProduct(Integer productId);

    List<ProductHistory> findPriceChange(Integer productDetailId);
}