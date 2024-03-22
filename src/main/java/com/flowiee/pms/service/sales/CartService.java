package com.flowiee.pms.service.sales;

import java.util.List;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.sales.OrderCart;

public interface CartService extends BaseService<OrderCart> {
    List<OrderCart> findCartByAccountId(Integer accountId);

    Double calTotalAmountWithoutDiscount(int cartId);

    boolean isItemExistsInCart(Integer cartId, Integer productVariantId);
}