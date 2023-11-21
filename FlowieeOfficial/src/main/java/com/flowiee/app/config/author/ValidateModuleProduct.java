package com.flowiee.app.config.author;

import com.flowiee.app.common.action.*;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.FlowieeUtil;
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

    private final String module = SystemModule.SAN_PHAM.name();

    public boolean readProduct() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.READ_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertProduct() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.CREATE_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateProduct() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.UPDATE_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteProduct() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.DELETE_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean importProduct() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.IMPORT_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateImage() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.UPDATE_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean priceManagement() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.UPDATE_PRICE_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean report() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.REPORT_SANPHAM.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readOrder() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DonHangAction.READ_DONHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertOrder() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DonHangAction.CREATE_DONHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateOrder() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DonHangAction.UPDATE_DONHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteOrder() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DonHangAction.DELETE_DONHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readCustomer() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhachHangAction.READ_KHACHHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertCustomer() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhachHangAction.CREATE_KHACHHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateCustomer() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhachHangAction.UPDATE_KHACHHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteCustomer() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhachHangAction.DELETE_KHACHHANG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readVoucher() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = VoucherAction.READ_VOUCHER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertVoucher() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = VoucherAction.CREATE_VOUCHER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateVoucher() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = VoucherAction.UPDATE_VOUCHER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteVoucher() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = VoucherAction.DELETE_VOUCHER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readSupplier() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SupplierAction.READ_SUPPLIER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertSupplier() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SupplierAction.CREATE_SUPPLIER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateSupplier() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SupplierAction.UPDATE_SUPPLIER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteSupplier() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SupplierAction.DELETE_SUPPLIER.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}