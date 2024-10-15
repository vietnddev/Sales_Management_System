package com.flowiee.pms.service.sales;

import java.util.List;

import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.sales.OrderCart;

public interface CartService extends BaseCurdService<OrderCart> {
    List<OrderCart> findCartByAccountId(Long accountId);

    Double calTotalAmountWithoutDiscount(long cartId);

    boolean isItemExistsInCart(Long cartId, Integer productVariantId);

    void resetCart(Long cartId);

    void addItemsToCart(Long cartId, String[] productVariantIds);

    void updateItemsOfCart(Items items, Long itemId);
}