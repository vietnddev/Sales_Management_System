package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.service.product.ProductPriceService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductPriceServiceImpl extends BaseService implements ProductPriceService {
    ProductVariantService   productVariantService;
    ProductDetailRepository productVariantRepo;
    ProductHistoryService   productHistoryService;

    @Transactional
    @Override
    public String updateProductPrice(Integer variantId, BigDecimal pOriginalPrice, BigDecimal pDiscountPrice) {
        try {
            Optional<ProductVariantDTO> productDetail = productVariantService.findById(variantId);
            if (productDetail.isEmpty()) {
                throw new BadRequestException();
            }
            productVariantRepo.updatePrice(pOriginalPrice != null ? pOriginalPrice : productDetail.get().getOriginalPrice(),
                                           pDiscountPrice != null ? pDiscountPrice : productDetail.get().getDiscountPrice(),
                                           productDetail.get().getRetailPrice(),
                                           productDetail.get().getRetailPriceDiscount(),
                                           productDetail.get().getWholesalePrice(),
                                           productDetail.get().getWholesalePriceDiscount(),
                                           productDetail.get().getPurchasePrice(),
                                           productDetail.get().getCostPrice(),
                                           variantId);
            //Log history change
            Integer productId = productDetail.get().getProductId();
            String title = "Cập nhật giá bán - giá %s";
            String oldValue = String.valueOf(productDetail.get().getOriginalPrice());
            String newValue = String.valueOf(pOriginalPrice);
            if (pOriginalPrice != null) {
                productHistoryService.save(new ProductHistory(productId, variantId, null, String.format(title, "gốc"), "PRICE", oldValue, newValue));
            }
            if (pOriginalPrice != null) {
                productHistoryService.save(new ProductHistory(productId, variantId, null, String.format(title, "giảm"), "PRICE", oldValue, newValue));
            }
            return MessageCode.UPDATE_SUCCESS.getDescription();
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.UPDATE_ERROR_OCCURRED.getDescription(), "price"), ex);
        }
    }
}