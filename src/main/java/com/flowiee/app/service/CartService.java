package com.flowiee.app.service;

import java.util.List;

import com.flowiee.app.entity.Items;
import com.flowiee.app.entity.OrderCart;

public interface CartService {
    List<OrderCart> findAllCarts();

    List<OrderCart> findCartByAccountId(Integer accountId);

    List<Items> findItemsByCartId(Integer cartId);

    OrderCart findCartById(Integer cartId);

    Items findItemById(Integer itemId);

    Items findItemByCartAndProductVariant(Integer cartId, Integer productVariantId);

    String saveCart(OrderCart orderCart);

    String updateCart(OrderCart orderCart, Integer cartId);

    String deleteCart(Integer cartId);

    String saveItem(Items items);

    String updateItem(Items items, Integer cartId);

    String deleteItem(Integer cartId);

    Integer findSoLuongByBienTheSanPhamId(Integer cartId, Integer productVariantId);

    Double calTotalAmountWithoutDiscount(int cartId);

    boolean isItemExistsInCart(Integer cartId, Integer productVariantId);

    void increaseItemQtyInCart(Integer itemId, int quantity);

    void deleteAllItems();
}