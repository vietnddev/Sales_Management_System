package com.flowiee.pms.service.sales;

import com.flowiee.pms.model.CartItemModel;
import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.sales.Items;

import java.util.List;

public interface CartItemsService extends BaseCurdService<Items> {
    List<CartItemModel> findAllItemsForSales();

    Integer findQuantityOfItem(Long cartId, Long productVariantId);

    Items findItemByCartAndProductVariant(Long cartId, Long productVariantId);

    void increaseItemQtyInCart(Long itemId, int quantity);

    void deleteAllItems();
}