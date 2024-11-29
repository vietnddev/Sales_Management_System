package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.product.ProductCombo;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.EntityNotFoundException;
import com.flowiee.pms.model.CartItemModel;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.sales.CartItemsRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductComboService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartItemsServiceImpl extends BaseService implements CartItemsService {
    CartItemsRepository mvCartItemsRepository;
    ProductComboService mvProductComboService;
    ProductVariantService mvProductVariantService;

    @Override
    public List<Items> findAll() {
        return mvCartItemsRepository.findAll();
    }

    @Override
    public Items findById(Long itemId, boolean pThrowException) {
        Optional<Items> entityOptional = mvCartItemsRepository.findById(itemId);
        if (entityOptional.isEmpty() && pThrowException) {
            throw new EntityNotFoundException(new Object[] {"cart item"}, null, null);
        }
        return entityOptional.orElse(null);
    }

    @Override
    public List<CartItemModel> findAllItemsForSales() {
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        List<ProductCombo> productCombos = mvProductComboService.findAll();
        List<ProductVariantDTO> productVariantDTOs = mvProductVariantService.findAll(-1, -1, null, null, null, null, null, true).getContent();
        for (ProductCombo productCbo : productCombos) {
            cartItemModelList.add(CartItemModel.builder()
                    .itemId(productCbo.getId())
                    .productComboId(productCbo.getId())
                    .productVariantId(-1l)
                    .itemName(productCbo.getComboName() + " - hiện còn " + productCbo.getQuantity())
                    .build());
        }
        for (ProductVariantDTO productVrt : productVariantDTOs) {
            cartItemModelList.add(CartItemModel.builder()
                    .itemId(productVrt.getId())
                    .productComboId(-1l)
                    .productVariantId(productVrt.getId())
                    .itemName(productVrt.getVariantName() + " - hiện còn " + productVrt.getAvailableSalesQty())
                    .build());
        }
        return cartItemModelList;
    }

    @Override
    public Integer findQuantityOfItem(Long cartId, Long productVariantId) {
        return mvCartItemsRepository.findQuantityByProductVariantId(cartId, productVariantId);
    }

    @Override
    public Items findItemByCartAndProductVariant(Long cartId, Long productVariantId) {
        return mvCartItemsRepository.findByCartAndProductVariant(cartId, productVariantId);
    }

    @Override
    public Items save(Items items) {
        if (items == null || items.getOrderCart() == null || items.getProductDetail() == null) {
            throw new BadRequestException();
        }
        return mvCartItemsRepository.save(items);
    }

    @Override
    public Items update(Items entity, Long entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return mvCartItemsRepository.save(entity);
    }

    @Override
    public String delete(Long itemId) {
        if (this.findById(itemId, true) == null) {
            throw new BadRequestException();
        }
        mvCartItemsRepository.deleteById(itemId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Transactional
    @Override
    public void increaseItemQtyInCart(Long itemId, int quantity) {
        mvCartItemsRepository.updateItemQty(itemId, quantity);
    }

    @Transactional
    @Override
    public void deleteAllItems() {
        mvCartItemsRepository.deleteAllItems();
    }
}