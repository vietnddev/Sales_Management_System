package com.flowiee.app.security;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.model.role.*;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleProduct extends BaseAuthorize {
    String module = SystemModule.PRODUCT.name();
    
    public boolean readDashboard() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_DASHBOARD.name());
    }

    public boolean readProduct() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_READ.name());
    }

    public boolean insertProduct() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_CREATE.name());
    }

    public boolean updateProduct() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_UPDATE.name());
    }

    public boolean deleteProduct() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_DELETE.name());
    }

    public boolean importProduct() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_IMPORT.name());
    }

    public boolean updateImage() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_UPDATE.name());
    }

    public boolean priceManagement() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_PRICE.name());
    }

    public boolean report() {
        return isAuthorized(module, ProductAction.PRO_PRODUCT_REPORT.name());
    }

    public boolean readOrder() {
        return isAuthorized(module, ProductAction.PRO_ORDERS_READ.name());
    }

    public boolean insertOrder() {
        return isAuthorized(module, ProductAction.PRO_ORDERS_CREATE.name());
    }

    public boolean updateOrder() {
        return isAuthorized(module, ProductAction.PRO_ORDERS_UPDATE.name());
    }

    public boolean deleteOrder() {
        return isAuthorized(module, ProductAction.PRO_ORDERS_DELETE.name());
    }

    public boolean readCustomer() {
        return isAuthorized(module, ProductAction.PRO_CUSTOMER_READ.name());
    }

    public boolean insertCustomer() {
        return isAuthorized(module, ProductAction.PRO_CUSTOMER_CREATE.name());
    }

    public boolean updateCustomer() {
        return isAuthorized(module, ProductAction.PRO_CUSTOMER_UPDATE.name());
    }

    public boolean deleteCustomer() {
        return isAuthorized(module, ProductAction.PRO_CUSTOMER_DELETE.name());
    }

    public boolean readVoucher() {
        return isAuthorized(module, ProductAction.PRO_VOUCHER_READ.name());
    }

    public boolean insertVoucher() {
        return isAuthorized(module, ProductAction.PRO_VOUCHER_CREATE.name());
    }

    public boolean updateVoucher() {
        return isAuthorized(module, ProductAction.PRO_VOUCHER_UPDATE.name());
    }

    public boolean deleteVoucher() {
        return isAuthorized(module, ProductAction.PRO_VOUCHER_DELETE.name());
    }

    public boolean readSupplier() {
        return isAuthorized(module, ProductAction.PRO_SUPPLIER_READ.name());
    }

    public boolean insertSupplier() {
        return isAuthorized(module, ProductAction.PRO_SUPPLIER_CREATE.name());
    }

    public boolean updateSupplier() {
        return isAuthorized(module, ProductAction.PRO_SUPPLIER_UPDATE.name());
    }

    public boolean deleteSupplier() {
        return isAuthorized(module, ProductAction.PRO_SUPPLIER_DELETE.name());
    }
}