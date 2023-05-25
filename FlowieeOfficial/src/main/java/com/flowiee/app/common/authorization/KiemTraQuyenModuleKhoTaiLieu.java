package com.flowiee.app.common.authorization;

import com.flowiee.app.common.utils.TaiKhoanUtil;
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

    public boolean kiemTraRoleXemDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.READ.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleThemMoiDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.CREATE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleCapNhatDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.UPDATE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleXoaDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.DELETE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleImportDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.IMPORT.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleDiChuyenDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.MOVE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleSaoChepDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.COPY.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleDownloadDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.DOWNLOAD.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleChiaSeDocument() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.SHARE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    /*
     * DOCTYPE
     * */
    public boolean kiemTraRoleReadDocType() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.READ_DOCTYPE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleCreateDocType() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.CREATE_DOCTYPE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleUpdateDocType() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.UPDATE_DOCTYPE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleDeleteDocType() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.DELETE_DOCTYPE.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    /*
    * DOCFIELD
    * */
    public boolean kiemTraRoleCreateDocField() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.CREATE_DOCFIELD.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleUpdateDocField() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.UPDATE_DOCFIELD.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraRoleDeleteDocField() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.DELETE_DOCFIELD.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}