package com.flowiee.pms.service.sales;

import java.util.List;

import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.service.BaseCurd;
import com.flowiee.pms.entity.sales.OrderCart;

public interface CartService extends BaseCurd<OrderCart> {
    List<OrderCart> findCartByAccountId(Integer accountId);

    Double calTotalAmountWithoutDiscount(int cartId);

    boolean isItemExistsInCart(Integer cartId, Integer productVariantId);

    void resetCart(Integer cartId);

    void addItemsToCart(Integer cartId, String[] productVariantIds);

    void updateItemsOfCart(Items items, Integer itemId);
}