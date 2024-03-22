package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.service.product.ProductPriceService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {
    Logger logger = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductDetailRepository productVariantRepo;
    @Autowired
    private ProductHistoryService productHistoryService;

    @Transactional
    @Override
    public String updateProductPrice(Integer variantId, BigDecimal originalPrice, BigDecimal discountPrice) {
        try {
            Optional<ProductVariantDTO> productDetail = productVariantService.findById(variantId);
            if (productDetail.isEmpty()) {
                throw new BadRequestException();
            }
            if (originalPrice == null) {
                originalPrice = productDetail.get().getOriginalPrice();
            }
            if (discountPrice == null) {
                discountPrice = productDetail.get().getDiscountPrice();
            }
            productVariantRepo.updatePrice(originalPrice, discountPrice, variantId);
            //Log history change
            Integer productId = productDetail.get().getProductId();
            String title = "Cập nhật giá bán - giá %s";
            String oldValue = String.valueOf(productDetail.get().getOriginalPrice());
            String newValue = String.valueOf(originalPrice);
            if (originalPrice != null) {
                productHistoryService.save(new ProductHistory(productId, variantId, null, String.format(title, "gốc"), "PRICE", oldValue, newValue));
            }
            if (originalPrice != null) {
                productHistoryService.save(new ProductHistory(productId, variantId, null, String.format(title, "giảm"), "PRICE", oldValue, newValue));
            }
            return MessageUtils.UPDATE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "price"), ex);
        }
    }
}