package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Product;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    byte[] exportData(List<Integer> listSanPhamId);

    List<Product> findByProductType(Integer productTypeId);

    List<Product> findByUnit(Integer unitId);

    List<Product> findByBrand(Integer brandId);
}