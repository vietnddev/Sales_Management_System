package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.Product;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    byte[] exportData(List<Integer> listSanPhamId);
}