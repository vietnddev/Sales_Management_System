package com.flowiee.app.common.authorization;

import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.role.service.AccountRoleService;
import com.flowiee.app.system.action.AccountAction;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModuleAccount {
    @Autowired
    private AccountRoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.NGUOI_DUNG.name();

    public boolean kiemTraQuyenXem() {
        if (accountService.getUserName().equals("superadmin")) {
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
        if (accountService.getUserName().equals("superadmin")) {
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
        if (accountService.getUserName().equals("superadmin")) {
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
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = AccountAction.DELETE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}