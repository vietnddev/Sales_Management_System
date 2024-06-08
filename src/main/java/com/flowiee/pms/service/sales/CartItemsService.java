package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.sales.Items;

public interface CartItemsService extends BaseCurdService<Items> {
    Integer findQuantityOfItem(Integer cartId, Integer productVariantId);

    Items findItemByCartAndProductVariant(Integer cartId, Integer productVariantId);

    void increaseItemQtyInCart(Integer itemId, int quantity);

    void deleteAllItems();
}