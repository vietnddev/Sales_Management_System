package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.ItemsRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemsServiceImpl extends BaseService implements CartItemsService {
    @Autowired
    private ItemsRepository itemsRepository;

    @Override
    public List<Items> findAll() {
        return itemsRepository.findAll();
    }

    @Override
    public Optional<Items> findById(Integer itemId) {
        return itemsRepository.findById(itemId);
    }

    @Override
    public Integer findQuantityOfItem(Integer cartId, Integer productVariantId) {
        return itemsRepository.findQuantityByProductVariantId(cartId, productVariantId);
    }

    @Override
    public Items findItemByCartAndProductVariant(Integer cartId, Integer productVariantId) {
        return itemsRepository.findByCartAndProductVariant(cartId, productVariantId);
    }

    @Override
    public Items save(Items items) {
        if (items == null || items.getOrderCart() == null || items.getProductDetail() == null) {
            throw new BadRequestException();
        }
        return itemsRepository.save(items);
    }

    @Override
    public Items update(Items entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return itemsRepository.save(entity);
    }

    @Override
    public String delete(Integer itemId) {
        if (this.findById(itemId).isEmpty()) {
            throw new BadRequestException();
        }
        itemsRepository.deleteById(itemId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Transactional
    @Override
    public void increaseItemQtyInCart(Integer itemId, int quantity) {
        itemsRepository.updateItemQty(itemId, quantity);
    }

    @Transactional
    @Override
    public void deleteAllItems() {
        itemsRepository.deleteAllItems();
    }
}