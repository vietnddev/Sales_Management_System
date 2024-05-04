package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.sales.ItemsRepository;
import com.flowiee.pms.repository.sales.OrderCartRepository;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.CartService;
import com.flowiee.pms.service.system.SystemLogService;

import com.flowiee.pms.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private OrderCartRepository cartRepository;
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private SystemLogService systemLogService;
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
        return "OK";
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
}