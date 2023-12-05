package com.flowiee.app.security.author;

import com.flowiee.app.model.role.*;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleProduct {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.PRODUCT.name();
    
    public boolean readDashboard() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_DASHBOARD.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readProduct() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_READ.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertProduct() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_CREATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateProduct() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteProduct() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_DELETE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean importProduct() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_IMPORT.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateImage() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean priceManagement() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_PRICE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean report() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_PRODUCT_REPORT.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readOrder() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_ORDERS_READ.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertOrder() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_ORDERS_CREATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateOrder() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_ORDERS_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteOrder() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_ORDERS_DELETE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readCustomer() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_CUSTOMER_READ.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertCustomer() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_CUSTOMER_CREATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateCustomer() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_CUSTOMER_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteCustomer() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_CUSTOMER_DELETE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readVoucher() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_VOUCHER_READ.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertVoucher() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_VOUCHER_CREATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateVoucher() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_VOUCHER_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteVoucher() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_VOUCHER_DELETE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readSupplier() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_SUPPLIER_READ.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertSupplier() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_SUPPLIER_CREATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateSupplier() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_SUPPLIER_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteSupplier() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = ProductAction.PRO_SUPPLIER_DELETE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}