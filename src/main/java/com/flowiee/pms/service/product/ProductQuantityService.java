package com.flowiee.pms.service.product;

public interface ProductQuantityService {
    void updateProductVariantQuantityIncrease(Integer pQuantity, Integer pProductVariantId);

    void updateProductVariantQuantityDecrease(Integer pQuantity, Integer pProductVariantId);
}