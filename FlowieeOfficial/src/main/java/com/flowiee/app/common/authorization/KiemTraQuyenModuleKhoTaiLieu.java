package com.flowiee.app.common.authorization;

import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.role.service.AccountRoleService;
import com.flowiee.app.system.action.AccountAction;
import com.flowiee.app.system.action.KhoTaiLieuAction;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModuleKhoTaiLieu {
    @Autowired
    private AccountRoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.KHO_TAI_LIEU.name();

    public boolean kiemTraQuyenXem() {
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = KhoTaiLieuAction.READ.name();
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
        final String action = KhoTaiLieuAction.CREATE.name();
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
        final String action = KhoTaiLieuAction.UPDATE.name();
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
        final String action = KhoTaiLieuAction.DELETE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenImport() {
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = KhoTaiLieuAction.IMPORT.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenDiChuyen() {
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = KhoTaiLieuAction.MOVE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenSaoChep() {
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = KhoTaiLieuAction.COPY.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenDownload() {
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = KhoTaiLieuAction.DOWNLOAD.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenChiaSe() {
        if (accountService.getUserName().equals("superadmin")) {
            return true;
        }
        final String action = KhoTaiLieuAction.SHARE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}