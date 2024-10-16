package com.flowiee.pms.service.product;

import java.math.BigDecimal;

public interface ProductPriceService {
    String updateProductPrice(Long variantId, BigDecimal originalPrice, BigDecimal discountPrice);
}