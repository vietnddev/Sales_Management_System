package com.flowiee.pms.service.product;

public interface ProductStatisticsService {
    Integer countTotalProductsInStorage();

    Integer findProductVariantTotalQtySell(Integer productId);

    Integer findProductVariantQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId);
}