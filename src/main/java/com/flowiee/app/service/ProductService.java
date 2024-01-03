package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService extends BaseService<Product> {
    List<ProductDTO> findAll(Integer productTypeId, Integer brandId, String status);

    byte[] exportData(List<Integer> listSanPhamId);

    List<Product> findByProductType(Integer productTypeId);

    List<Product> findByUnit(Integer unitId);

    List<Product> findByBrand(Integer brandId);

    boolean productInUse(Integer productId);
}