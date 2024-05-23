package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.sales.CartItemsRepository;
import com.flowiee.pms.repository.sales.OrderCartRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.service.sales.CartService;

import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl extends BaseService implements CartService {
    @Autowired
    private OrderCartRepository cartRepository;
    @Autowired
    private CartItemsService cartItemsService;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private ProductVariantService productVariantService;

    @Override
    public List<OrderCart> findCartByAccountId(Integer accountId) {
        List<OrderCart> listCart = cartRepository.findByAccountId(accountId);
        for (OrderCart cart : listCart) {
            for (Items item : cart.getListItems()) {
                Optional<ProductVariantDTO> productDetail = productVariantService.findById(item.getProductDetail().getId());
                if (productDetail.isPresent()) {
                    BigDecimal originalPrice = productDetail.get().getOriginalPrice();
                    BigDecimal discountPrice = productDetail.get().getDiscountPrice();
                    item.setPrice(discountPrice != null ? discountPrice : originalPrice);
                }
            }
        }
        return listCart;
    }

    @Override
    public List<OrderCart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Optional<OrderCart> findById(Integer id) {
        return cartRepository.findById(id);
    }

    @Override
    public OrderCart save(OrderCart orderCart) {
        if (orderCart == null) {
            throw new BadRequestException();
        }
        return cartRepository.save(orderCart);
    }

    @Override
    public OrderCart update(OrderCart cart, Integer cartId) {
        if (this.findById(cartId).isEmpty()) {
            throw new BadRequestException();
        }
        cart.setId(cartId);
        return cartRepository.save(cart);
    }

    @Override
    public String delete(Integer id) {
        if (this.findById(id).isEmpty()) {
            throw new BadRequestException();
        }
        cartRepository.deleteById(id);
        SystemLog systemLog = new SystemLog();
        systemLog.setModule(MODULE.PRODUCT.name());
        systemLog.setAction("DELETE_CART");
        systemLog.setCreatedBy(CommonUtils.getUserPrincipal().getId());
        systemLog.setIp(CommonUtils.getUserPrincipal().getIp());
        systemLog.setContent("DELETE CART");
        systemLogService.writeLog(systemLog);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public Double calTotalAmountWithoutDiscount(int cartId) {
        return cartItemsRepository.calTotalAmountWithoutDiscount(cartId);
    }

    @Override
    public boolean isItemExistsInCart(Integer cartId, Integer productVariantId) {
        Items item = cartItemsRepository.findByCartAndProductVariant(cartId, productVariantId);
        return item != null;
    }

    @Transactional
    @Override
    public void resetCart(Integer cartId) {
        Optional<OrderCart> cartOptional = this.findById(cartId);
        cartOptional.ifPresent(orderCart -> cartItemsRepository.deleteAllItems(orderCart.getId()));
    }

    @Override
    public void addItemsToCart(Integer cartId, String[] productVariantIds) {
        List<String> listProductVariantId = Arrays.stream(productVariantIds).toList();
        for (String productVariantId : listProductVariantId) {
            Optional<ProductVariantDTO> productVariant = productVariantService.findById(Integer.parseInt(productVariantId));
            if (productVariant.isEmpty()) {
                continue;
            }
            if (this.isItemExistsInCart(cartId, productVariant.get().getId())) {
                Items items = cartItemsService.findItemByCartAndProductVariant(cartId, productVariant.get().getId());
                cartItemsService.increaseItemQtyInCart(items.getId(), items.getQuantity() + 1);
            } else {
                Items items = new Items();
                items.setOrderCart(new OrderCart(cartId));
                items.setProductDetail(new ProductDetail(Integer.parseInt(productVariantId)));
                items.setPriceType(AppConstants.PRICE_TYPE.L.name());
                items.setPrice(productVariant.get().getRetailPriceDiscount() != null ? productVariant.get().getRetailPriceDiscount() : productVariant.get().getRetailPrice());
                items.setPriceOriginal(productVariant.get().getRetailPrice());
                items.setExtraDiscount(BigDecimal.ZERO);
                items.setQuantity(1);
                items.setNote("");
                cartItemsService.save(items);
            }
        }
    }

    @Override
    public void updateItemsOfCart(Items itemToUpdate, Integer itemId) {
        Optional<Items> itemOptional = cartItemsService.findById(itemId);
        if (itemOptional.isEmpty()) {
            throw new BadRequestException("Item not found!");
        }
        Items item = itemOptional.get();
        if (itemToUpdate.getQuantity() <= 0) {
            cartItemsService.delete(item.getId());
        } else {
            ProductDetail productVariant = item.getProductDetail();
            item.setNote(itemToUpdate.getNote());
            item.setQuantity(itemToUpdate.getQuantity());
            if (itemToUpdate.getPriceType() != null && (!item.getPriceType().equals(itemToUpdate.getPriceType()))) {
                if (itemToUpdate.getPriceType().equals(AppConstants.PRICE_TYPE.L.name())) {
                    item.setPrice(productVariant.getRetailPriceDiscount());
                    item.setPriceOriginal(productVariant.getRetailPrice());
                    item.setPriceType(AppConstants.PRICE_TYPE.L.name());
                }
                if (itemToUpdate.getPriceType().equals(AppConstants.PRICE_TYPE.S.name())) {
                    item.setPrice(productVariant.getWholesalePriceDiscount());
                    item.setPriceOriginal(productVariant.getWholesalePrice());
                    item.setPriceType(AppConstants.PRICE_TYPE.S.name());
                }
            }
            if (itemToUpdate.getExtraDiscount() != null) {
                item.setExtraDiscount(itemToUpdate.getExtraDiscount());
            }
            cartItemsService.update(item, item.getId());
        }
    }
}