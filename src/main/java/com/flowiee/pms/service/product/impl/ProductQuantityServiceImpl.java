package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.constants.MasterObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductQuantityServiceImpl extends BaseService implements ProductQuantityService {
    SystemLogService mvSystemLogService;
    ProductDetailRepository mvProductVariantRepository;

    @Transactional
    @Override
    public void updateProductVariantQuantityIncrease(Integer pQuantity, Integer pProductVariantId) {
        this.updateProductVariantQuantity(pQuantity, pProductVariantId, "I");
    }

    @Transactional
    @Override
    public void updateProductVariantQuantityDecrease(Integer pQuantity, Integer pProductVariantId) {
        this.updateProductVariantQuantity(pQuantity, pProductVariantId, "D");
    }

    private void updateProductVariantQuantity(Integer quantity, Integer productVariantId, String type) {
        try {
            if ("I".equals(type)) {
                mvProductVariantRepository.updateQuantityIncrease(quantity, productVariantId);
            } else if ("D".equals(type)) {
                mvProductVariantRepository.updateQuantityDecrease(quantity, productVariantId);
            }
            mvSystemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductVariant, "Cập nhật số lượng sản phẩm", "productVariantId = " + productVariantId);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.UPDATE_ERROR_OCCURRED.getDescription(), "product quantity"), ex);
        }
    }
}