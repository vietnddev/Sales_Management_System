package com.flowiee.app.common.authorization;

import com.flowiee.app.common.utils.TaiKhoanUtil;
import com.flowiee.app.account.service.AccountService;
import com.flowiee.app.role.service.AccountRoleService;
import com.flowiee.app.system.action.AccountAction;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModulePhanQuyen {
    @Autowired
    private AccountRoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.PHAN_QUYEN.name();

    public boolean kiemTraQuyenXem() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = AccountAction.READ.name();
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
        final String action = AccountAction.CREATE.name();
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
        final String action = AccountAction.UPDATE.name();
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
        final String action = AccountAction.DELETE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenDoiMatKhau() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = AccountAction.RESET_PASSWORD.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenPhanQuyen() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = AccountAction.SHARE_ROLE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}