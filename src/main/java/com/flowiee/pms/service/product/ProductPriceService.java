package com.flowiee.pms.service.product;

import java.math.BigDecimal;

public interface ProductPriceService {
    String updateProductPrice(Integer variantId, BigDecimal originalPrice, BigDecimal discountPrice);
}