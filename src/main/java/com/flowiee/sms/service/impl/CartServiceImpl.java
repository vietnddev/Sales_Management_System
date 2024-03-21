package com.flowiee.sms.service.impl;

import com.flowiee.sms.entity.Items;
import com.flowiee.sms.entity.OrderCart;
import com.flowiee.sms.entity.ProductDetail;
import com.flowiee.sms.entity.SystemLog;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.repository.ItemsRepository;
import com.flowiee.sms.repository.OrderCartRepository;
import com.flowiee.sms.service.CartService;
import com.flowiee.sms.service.ProductService;
import com.flowiee.sms.service.SystemLogService;

import com.flowiee.sms.utils.CommonUtils;
import com.flowiee.sms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired private OrderCartRepository cartRepository;
    @Autowired private ItemsRepository itemsRepository;
    @Autowired private SystemLogService systemLogService;
    @Autowired private ProductService productService;

    @Override
    public List<OrderCart> findAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public List<OrderCart> findCartByAccountId(Integer accountId) {
        List<OrderCart> listCart = cartRepository.findByAccountId(accountId);
        for (OrderCart cart : listCart) {
            for (Items item : cart.getListItems()) {
                ProductDetail productDetail = productService.findProductVariantById(item.getProductDetail().getId());
                if (productDetail != null) {
                    item.setPrice(productDetail.getDiscountPrice() != null ? productDetail.getDiscountPrice() : productDetail.getOriginalPrice());
                }
            }
        }
        return listCart;
    }

    @Override
    public List<Items> findItemsByCartId(Integer cartId) {
        return itemsRepository.findByCartId(cartId);
    }

    @Override
    public OrderCart findCartById(Integer id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public Items findItemById(Integer itemId) {
        return itemsRepository.findById(itemId).orElse(null);
    }

    @Override
    public Items findItemByCartAndProductVariant(Integer cartId, Integer productVariantId) {
        return itemsRepository.findByCartAndProductVariant(cartId, productVariantId);
    }

    @Override
    public OrderCart saveCart(OrderCart orderCart) {
        if (orderCart == null) {
            throw new BadRequestException();
        }
        return cartRepository.save(orderCart);
    }

    @Override
    public OrderCart updateCart(OrderCart cart, Integer cartId) {
        cart.setId(cartId);
        return cartRepository.save(cart);
    }

    @Override
    public String deleteCart(Integer id) {
        cartRepository.deleteById(id);
        SystemLog systemLog = new SystemLog();
        systemLog.setModule(MODULE.PRODUCT.name());
        systemLog.setAction("DELETE_CART");
        systemLog.setCreatedBy(CommonUtils.getUserPrincipal().getId());
        systemLog.setIp(CommonUtils.getUserPrincipal().getIp());
        systemLog.setContent("DELETE CART");
        systemLogService.writeLog(systemLog);
        return "OK";
    }

    @Override
    public Items saveItem(Items items) {
        if (items == null) {
            throw new BadRequestException();
        }
        return itemsRepository.save(items);
    }

    @Override
    public Items updateItem(Items entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return itemsRepository.save(entity);
    }

    @Override
    public String deleteItem(Integer itemId) {
        Items items = this.findItemById(itemId);
        if (items == null) {
            throw new BadRequestException();
        }
        itemsRepository.deleteById(itemId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public Integer findSoLuongByBienTheSanPhamId(Integer cartId, Integer productVariantId) {
        return itemsRepository.findQuantityByProductVariantId(cartId, productVariantId);
    }

    @Override
    public Double calTotalAmountWithoutDiscount(int cartId) {
        return itemsRepository.calTotalAmountWithoutDiscount(cartId);
    }

    @Override
    public boolean isItemExistsInCart(Integer cartId, Integer productVariantId) {
        Items item = itemsRepository.findByCartAndProductVariant(cartId, productVariantId);
        return item != null;
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