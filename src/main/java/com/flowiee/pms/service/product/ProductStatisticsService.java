package com.flowiee.pms.service.product;

public interface ProductStatisticsService {
    Integer countTotalProductsInStorage();

    Integer findProductVariantTotalQtySell(Long productId);

    Integer findProductVariantQuantityBySizeOfEachColor(Long productId, Long colorId, Long sizeId);
}