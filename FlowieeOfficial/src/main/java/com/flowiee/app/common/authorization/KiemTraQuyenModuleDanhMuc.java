package com.flowiee.app.common.authorization;

import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.role.service.AccountRoleService;
import com.flowiee.app.system.action.AccountAction;
import com.flowiee.app.system.action.DanhMucAction;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModuleDanhMuc {
    @Autowired
    private AccountRoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.DANH_MUC.name();

    public boolean kiemTraQuyenXem() {
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = DanhMucAction.READ.name();
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
        final String action = DanhMucAction.CREATE.name();
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
        final String action = DanhMucAction.UPDATE.name();
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
        final String action = DanhMucAction.DELETE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}