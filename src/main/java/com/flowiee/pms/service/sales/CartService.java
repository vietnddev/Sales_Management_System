package com.flowiee.pms.service.sales;

import java.util.List;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.sales.OrderCart;

public interface CartService extends CrudService<OrderCart> {
    List<OrderCart> findCartByAccountId(Integer accountId);

    Double calTotalAmountWithoutDiscount(int cartId);

    boolean isItemExistsInCart(Integer cartId, Integer productVariantId);
}