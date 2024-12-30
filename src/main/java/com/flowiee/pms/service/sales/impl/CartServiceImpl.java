package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductPrice;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.EntityNotFoundException;
import com.flowiee.pms.model.payload.CartItemsReq;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.sales.CartItemsRepository;
import com.flowiee.pms.repository.sales.OrderCartRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.service.sales.CartService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartServiceImpl extends BaseService implements CartService {
    CartItemsService mvCartItemsService;
    OrderCartRepository mvCartRepository;
    CartItemsRepository mvCartItemsRepository;
    ProductVariantService mvProductVariantService;

    @Override
    public List<OrderCart> findCartByAccountId(Long accountId) {
        List<OrderCart> listCart = mvCartRepository.findByAccountId(accountId);
        for (OrderCart cart : listCart) {
            for (Items item : cart.getListItems()) {
                ProductPrice itemPrice = item.getProductDetail().getVariantPrice();//mvProductPriceRepository.findPricePresent(null, item.getProductDetail().getId());
                if (itemPrice != null) {
                    PriceType priceType = PriceType.valueOf(item.getPriceType());
                    if (priceType.equals(PriceType.L)) {
                        item.setPriceOriginal(itemPrice.getRetailPrice());
                        item.setPrice(itemPrice.getRetailPriceDiscount());
                    }
                    if (priceType.equals(PriceType.S)) {
                        item.setPriceOriginal(itemPrice.getWholesalePrice());
                        item.setPrice(itemPrice.getWholesalePriceDiscount());
                    }
                }
                item.getProductDetail().setAvailableSalesQty(item.getProductDetail().getStorageQty() - item.getProductDetail().getDefectiveQty());
            }
        }
        return listCart;
    }

    @Override
    public List<OrderCart> findAll() {
        return mvCartRepository.findAll();
    }

    @Override
    public OrderCart findById(Long id, boolean pThrowException) {
        Optional<OrderCart> optionalCart = mvCartRepository.findById(id);
        if (optionalCart.isEmpty() && pThrowException) {
            throw new EntityNotFoundException(new Object[] {"cart"}, null, null);
        }
        return optionalCart.orElse(null);
    }

    @Override
    public OrderCart save(OrderCart orderCart) {
        if (orderCart == null) {
            throw new BadRequestException();
        }
        return mvCartRepository.save(orderCart);
    }

    @Override
    public OrderCart update(OrderCart cart, Long cartId) {
        if (this.findById(cartId, true) == null) {
            throw new BadRequestException();
        }
        cart.setId(cartId);
        return mvCartRepository.save(cart);
    }

    @Override
    public String delete(Long cartId) {
        if (this.findById(cartId, true) == null) {
            throw new BadRequestException();
        }
        mvCartRepository.deleteById(cartId);
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_CART_C, MasterObject.Cart, "Xóa/Reset giỏ hàng", "cartId = " + cartId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public Double calTotalAmountWithoutDiscount(long cartId) {
        return mvCartItemsRepository.calTotalAmountWithoutDiscount(cartId);
    }

    @Override
    public boolean isItemExistsInCart(Long cartId, Long productVariantId) {
        Items item = mvCartItemsRepository.findByCartAndProductVariant(cartId, productVariantId);
        return item != null;
    }

    @Transactional
    @Override
    public void resetCart(Long cartId) {
        OrderCart cart = this.findById(cartId, true);
        mvCartItemsRepository.deleteAllItems(cart.getId());
    }

    @Override
    public void addItemsToCart(Long cartId, String[] productVariantIds) {
        List<String> listProductVariantId = Arrays.stream(productVariantIds).toList();
        for (String productVariantId : listProductVariantId) {
            ProductVariantDTO productVariant = mvProductVariantService.findById(Long.parseLong(productVariantId), false);
            if (productVariant == null) {
                continue;
            }
            if (productVariant.getAvailableSalesQty() == 0) {
                throw new AppException(ErrorCode.ProductOutOfStock, new Object[]{productVariant.getVariantName()}, null, getClass(), null);
            }
            if (this.isItemExistsInCart(cartId, productVariant.getId())) {
                Items items = mvCartItemsService.findItemByCartAndProductVariant(cartId, productVariant.getId());
                mvCartItemsService.increaseItemQtyInCart(items.getId(), items.getQuantity() + 1);
            } else {
                ProductPrice productVariantPrice = productVariant.getVariantPrice();//mvProductPriceRepository.findPricePresent(null, Long.parseLong(productVariantId));
                if (productVariantPrice == null) {
                    throw new AppException(String.format("Sản phẩm %s chưa được thiết lập giá bán!", productVariant.getVariantName()));
                }
                BigDecimal lvRetailPrice = productVariantPrice.getRetailPrice();
                BigDecimal lvRetailPriceDiscount = productVariantPrice.getRetailPriceDiscount();
                Items items = Items.builder()
                    .orderCart(new OrderCart(cartId))
                    .productDetail(new ProductDetail(Integer.parseInt(productVariantId)))
                    .priceType(PriceType.L.name())
                    .price(lvRetailPriceDiscount != null ? lvRetailPriceDiscount : lvRetailPrice)
                    .priceOriginal(lvRetailPrice)
                    .extraDiscount(BigDecimal.ZERO)
                    .quantity(1)
                    .note("")
                    .build();
                mvCartItemsService.save(items);
            }
        }
    }

    @Transactional
    @Override
    public void addItemsToCart(CartItemsReq cartItemsReq) {
        Long lvCartId = cartItemsReq.getCartId();
        OrderCart orderCart = findById(lvCartId, true);

        List<Items> itemsList = cartItemsReq.getItems();
        if (ObjectUtils.isEmpty(itemsList)) {
            throw new BadRequestException("Please choose at least one product!");
        }

        for (Items item : itemsList) {
            ProductVariantDTO lvProductVariant = mvProductVariantService.findById(item.getProductVariantId(), false);
            if (lvProductVariant == null) {
                continue;
            }
            Integer lvItemQty = item.getQuantity();
            if (lvItemQty <= 0) {
                throw new BadRequestException("Vui lòng nhập số lượng cho sản phẩm: " + lvProductVariant.getVariantName());
            }
            if (lvProductVariant.getAvailableSalesQty() == 0 || lvProductVariant.getAvailableSalesQty() < lvItemQty) {
                throw new AppException(ErrorCode.ProductOutOfStock, new Object[]{lvProductVariant.getVariantName()}, null, getClass(), null);
            }
            if (this.isItemExistsInCart(lvCartId, lvProductVariant.getId())) {
                Items items = mvCartItemsService.findItemByCartAndProductVariant(lvCartId, lvProductVariant.getId());
                mvCartItemsService.increaseItemQtyInCart(items.getId(), items.getQuantity() + 1);
            } else {
                ProductPrice productVariantPrice = lvProductVariant.getVariantPrice();//mvProductPriceRepository.findPricePresent(null, Long.parseLong(productVariantId));
                if (productVariantPrice == null) {
                    throw new AppException(String.format("Sản phẩm %s chưa được thiết lập giá bán!", lvProductVariant.getVariantName()));
                }
                BigDecimal lvRetailPrice = productVariantPrice.getRetailPrice();
                BigDecimal lvRetailPriceDiscount = productVariantPrice.getRetailPriceDiscount();
                Items items = Items.builder()
                        .orderCart(orderCart)
                        .productDetail(lvProductVariant)
                        .priceType(PriceType.L.name())
                        .price(lvRetailPriceDiscount != null ? lvRetailPriceDiscount : lvRetailPrice)
                        .priceOriginal(lvRetailPrice)
                        .extraDiscount(BigDecimal.ZERO)
                        .quantity(lvItemQty)
                        .note("")
                        .build();
                mvCartItemsService.save(items);
            }
        }
    }

    @Override
    public void updateItemsOfCart(Items itemToUpdate, Long itemId) {
        Items item = mvCartItemsService.findById(itemId, true);
        if (itemToUpdate.getQuantity() <= 0) {
            mvCartItemsService.delete(item.getId());
        } else {
            ProductDetail productVariant = item.getProductDetail();
            if (itemToUpdate.getQuantity() > productVariant.getAvailableSalesQty()) {
                throw new AppException(ErrorCode.ProductOutOfStock, new Object[]{productVariant.getVariantName()}, null, getClass(), null);
            }
            ProductPrice productVariantPrice = productVariant.getVariantPrice();//mvProductPriceRepository.findPricePresent(null, productVariant.getId());
            String lvPriceType = itemToUpdate.getPriceType();
            BigDecimal lvRetailPrice = productVariantPrice.getRetailPrice();
            BigDecimal lvRetailPriceDiscount = productVariantPrice.getRetailPriceDiscount();
            BigDecimal lvWholesalePrice = productVariantPrice.getWholesalePrice();
            BigDecimal lvWholesalePriceDiscount = productVariantPrice.getWholesalePriceDiscount();

            item.setNote(itemToUpdate.getNote());
            item.setQuantity(itemToUpdate.getQuantity());
            if (lvPriceType != null && (!item.getPriceType().equals(lvPriceType))) {
                if (lvPriceType.equals(PriceType.L.name())) {
                    item.setPrice(lvRetailPriceDiscount);
                    item.setPriceOriginal(lvRetailPrice);
                    item.setPriceType(PriceType.L.name());
                }
                if (lvPriceType.equals(PriceType.S.name())) {
                    item.setPrice(lvWholesalePriceDiscount);
                    item.setPriceOriginal(lvWholesalePrice);
                    item.setPriceType(PriceType.S.name());
                }
            }
            if (itemToUpdate.getExtraDiscount() != null) {
                item.setExtraDiscount(itemToUpdate.getExtraDiscount());
            }
            mvCartItemsService.update(item, item.getId());
        }
    }
}