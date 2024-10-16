package com.flowiee.pms.service.product;

public interface ProductQuantityService {
    void updateProductVariantQuantityIncrease(Integer pQuantity, Long pProductVariantId);

    void updateProductVariantQuantityDecrease(Integer pQuantity, Long pProductVariantId);
}