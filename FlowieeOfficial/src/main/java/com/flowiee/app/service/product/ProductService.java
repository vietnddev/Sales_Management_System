package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.product.Product;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    byte[] exportData(List<Integer> listSanPhamId);
}