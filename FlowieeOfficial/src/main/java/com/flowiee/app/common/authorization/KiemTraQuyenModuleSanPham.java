package com.flowiee.app.common.authorization;

import com.flowiee.app.common.utils.TaiKhoanUtil;
import com.flowiee.app.account.service.AccountService;
import com.flowiee.app.role.service.AccountRoleService;
import com.flowiee.app.system.action.SanPhamAction;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModuleSanPham {
    @Autowired
    private AccountRoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.SAN_PHAM.name();

    public boolean kiemTraQuyenXem() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.READ.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenThemMoi() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.CREATE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenCapNhat() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.UPDATE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenXoa() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.DELETE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenImport() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.IMPORT.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenUploadHinhAnh() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.UPLOAD_IMAGE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenQuanLyGiaBan() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.PRICE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenBaoCaoThongKe() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SanPhamAction.REPORT.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}