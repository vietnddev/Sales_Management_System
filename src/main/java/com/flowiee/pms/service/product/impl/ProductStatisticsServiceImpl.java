package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductStatisticsServiceImpl extends BaseService implements ProductStatisticsService {
    @Autowired
    private ProductDetailRepository productVariantRepo;

    @Override
    public Integer countTotalProductsInStorage() {
        return productVariantRepo.countTotalQuantity();
    }

    @Override
    public Integer findProductVariantTotalQtySell(Integer productId) {
        return productVariantRepo.findTotalQtySell(productId);
    }

    @Override
    public Integer findProductVariantQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId) {
        try {
            return productVariantRepo.findQuantityBySizeOfEachColor(productId, colorId, sizeId);
        } catch (RuntimeException ex) {
            System.out.println("productId " + productId + ", colorId " + colorId + ", sizeId " + sizeId);
            throw new RuntimeException("Error finding product variant quantity", ex);
        }
    }
}