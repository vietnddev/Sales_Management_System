package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.service.system.ConfigService;
import com.flowiee.pms.service.system.MailMediaService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.system.SystemLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductQuantityServiceImpl extends BaseService implements ProductQuantityService {
    ProductDetailRepository mvProductVariantRepository;
    AccountRepository mvAccountRepository;
    SystemLogService mvSystemLogService;
    MailMediaService mvMailMediaService;
    ConfigService mvConfigService;

    @Transactional
    @Override
    public void updateProductVariantQuantityIncrease(Integer pQuantity, Long pProductVariantId) {
        this.updateProductVariantQuantity(pQuantity, pProductVariantId, "I");
    }

    @Transactional
    @Override
    public void updateProductVariantQuantityDecrease(Integer pQuantity, Long pProductVariantId) {
        this.updateProductVariantQuantity(pQuantity, pProductVariantId, "D");
    }

    private void updateProductVariantQuantity(Integer pQuantity, Long productVariantId, String type) {
        ProductDetail lvProductDetail = mvProductVariantRepository.findById(productVariantId).orElseThrow(() -> new AppException("Product not found!"));
        Integer lvLowStockThreshold = lvProductDetail.getLowStockThreshold();
        int lvCurrentQuantity = lvProductDetail.getStorageQty();
        try {
            if ("I".equals(type)) {
                mvProductVariantRepository.updateQuantityIncrease(pQuantity, productVariantId);
            } else if ("D".equals(type)) {
                if (lvCurrentQuantity < pQuantity)
                    throw new BadRequestException("Hàng tồn kho không đủ số lượng!");

                lvProductDetail.setStorageQty(lvCurrentQuantity - pQuantity);
                lvProductDetail.setSoldQty(lvProductDetail.getSoldQty() + pQuantity);
                ProductDetail productDetailUpdated = mvProductVariantRepository.save(lvProductDetail);

                if (lvLowStockThreshold != null && productDetailUpdated.getStorageQty() <= lvLowStockThreshold) {
                    SystemConfig lvLowStockAlertMdl = mvConfigService.getSystemConfig(ConfigCode.lowStockAlert.name());
                    if (isConfigAvailable(lvLowStockAlertMdl) && lvLowStockAlertMdl.isYesOption()) {
                        sendNotifyWarningLowStock(productDetailUpdated);
                    }
                }
            }
            mvSystemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductVariant, "Cập nhật số lượng sản phẩm", "productVariantId = " + productVariantId);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.UPDATE_ERROR_OCCURRED.getDescription(), "product quantity"), ex);
        }
    }

    private void sendNotifyWarningLowStock(ProductDetail pProductDetail) {
        Account lvAdmin = mvAccountRepository.findByUsername(AppConstants.ADMINISTRATOR);
        if (lvAdmin == null)
            return;

        String lvDestination = lvAdmin.getEmail();
        if (lvDestination == null || lvDestination.isBlank())
            return;

        Map<String, Object> lvNotificationParameter = new HashMap<>();
        lvNotificationParameter.put(NotificationType.LowStockAlert.name(), lvDestination);
        lvNotificationParameter.put("productName", pProductDetail.getVariantName());
        lvNotificationParameter.put("currentQuantity", pProductDetail.getStorageQty());
        lvNotificationParameter.put("threshold", pProductDetail.getLowStockThreshold());

        mvMailMediaService.send(NotificationType.LowStockAlert, lvNotificationParameter);
    }
}