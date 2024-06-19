package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.CartItemsRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartItemsServiceImpl extends BaseService implements CartItemsService {
    CartItemsRepository cartItemsRepository;

    @Override
    public List<Items> findAll() {
        return cartItemsRepository.findAll();
    }

    @Override
    public Optional<Items> findById(Integer itemId) {
        return cartItemsRepository.findById(itemId);
    }

    @Override
    public Integer findQuantityOfItem(Integer cartId, Integer productVariantId) {
        return cartItemsRepository.findQuantityByProductVariantId(cartId, productVariantId);
    }

    @Override
    public Items findItemByCartAndProductVariant(Integer cartId, Integer productVariantId) {
        return cartItemsRepository.findByCartAndProductVariant(cartId, productVariantId);
    }

    @Override
    public Items save(Items items) {
        if (items == null || items.getOrderCart() == null || items.getProductDetail() == null) {
            throw new BadRequestException();
        }
        return cartItemsRepository.save(items);
    }

    @Override
    public Items update(Items entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return cartItemsRepository.save(entity);
    }

    @Override
    public String delete(Integer itemId) {
        if (this.findById(itemId).isEmpty()) {
            throw new BadRequestException();
        }
        cartItemsRepository.deleteById(itemId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Transactional
    @Override
    public void increaseItemQtyInCart(Integer itemId, int quantity) {
        cartItemsRepository.updateItemQty(itemId, quantity);
    }

    @Transactional
    @Override
    public void deleteAllItems() {
        cartItemsRepository.deleteAllItems();
    }
}