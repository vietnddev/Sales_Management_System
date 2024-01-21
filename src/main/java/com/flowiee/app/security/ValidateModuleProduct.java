package com.flowiee.app.security;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.utils.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleProduct extends BaseAuthorize {
    String module = AppConstants.SYSTEM_MODULE.PRODUCT.name();
    
    public boolean readDashboard(boolean throwException) {
        return isAuthorized(module, AppConstants.DASHBOARD_ACTION.READ_DASHBOARD.name(), throwException);
    }

    public boolean readProduct(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_READ.name(), throwException);
    }

    public boolean insertProduct(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_CREATE.name(), throwException);
    }

    public boolean updateProduct(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), throwException);
    }

    public boolean deleteProduct(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_DELETE.name(), throwException);
    }

    public boolean importProduct(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_IMPORT.name(), throwException);
    }

    public boolean updateImage(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), throwException);
    }

    public boolean priceManagement(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_PRICE.name(), throwException);
    }

    public boolean report(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_REPORT.name(), throwException);
    }

    public boolean readOrder(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_READ.name(), throwException);
    }

    public boolean insertOrder(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_CREATE.name(), throwException);
    }

    public boolean updateOrder(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_UPDATE.name(), throwException);
    }

    public boolean deleteOrder(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_ORDERS_DELETE.name(), throwException);
    }

    public boolean readCustomer(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_CUSTOMER_READ.name(), throwException);
    }

    public boolean insertCustomer(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_CUSTOMER_CREATE.name(), throwException);
    }

    public boolean updateCustomer(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_CUSTOMER_UPDATE.name(), throwException);
    }

    public boolean deleteCustomer(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_CUSTOMER_DELETE.name(), throwException);
    }

    public boolean readVoucher(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_VOUCHER_READ.name(), throwException);
    }

    public boolean insertVoucher(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_VOUCHER_CREATE.name(), throwException);
    }

    public boolean updateVoucher(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_VOUCHER_UPDATE.name(), throwException);
    }

    public boolean deleteVoucher(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_VOUCHER_DELETE.name(), throwException);
    }

    public boolean readSupplier(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_SUPPLIER_READ.name(), throwException);
    }

    public boolean insertSupplier(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_SUPPLIER_CREATE.name(), throwException);
    }

    public boolean updateSupplier(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_SUPPLIER_UPDATE.name(), throwException);
    }

    public boolean deleteSupplier(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_SUPPLIER_DELETE.name(), throwException);
    }

    public boolean readGallery(boolean throwException) {
        return isAuthorized(module, AppConstants.PRODUCT_ACTION.PRO_GALLERY_READ.name(), throwException);
    }
}