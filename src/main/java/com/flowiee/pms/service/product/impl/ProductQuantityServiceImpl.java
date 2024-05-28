package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductQuantityServiceImpl extends BaseService implements ProductQuantityService {
    private static final String mainObjectName = "ProductVariant";

    @Autowired
    private ProductDetailRepository productVariantRepo;
    @Autowired
    private SystemLogService systemLogService;

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
                productVariantRepo.updateQuantityIncrease(quantity, productVariantId);
            } else if ("D".equals(type)) {
                productVariantRepo.updateQuantityDecrease(quantity, productVariantId);
            }
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_PRD_U.name(), mainObjectName, LogType.U.name(), "Update product quantity");
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product quantity"), ex);
        }
    }
}