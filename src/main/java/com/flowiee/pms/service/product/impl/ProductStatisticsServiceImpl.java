package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductStatisticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductStatisticsServiceImpl extends BaseService implements ProductStatisticsService {
    ProductDetailRepository productVariantRepo;

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