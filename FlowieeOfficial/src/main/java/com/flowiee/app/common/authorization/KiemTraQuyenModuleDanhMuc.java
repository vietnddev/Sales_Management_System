package com.flowiee.app.common.authorization;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.RoleService;
import com.flowiee.app.hethong.model.action.DanhMucAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModuleDanhMuc {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.DANH_MUC.name();

    public boolean kiemTraQuyenXem() {
        if (FlowieeUtil.ACCOUNT_USERNAME.equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.READ_DANHMUC.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.ACCOUNT_USERNAME);
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenThemMoi() {
        if (FlowieeUtil.ACCOUNT_USERNAME.equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.CREATE_DANHMUC.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.ACCOUNT_USERNAME);
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenCapNhat() {
        if (FlowieeUtil.ACCOUNT_USERNAME.equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.UPDATE_DANHMUC.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.ACCOUNT_USERNAME);
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenXoa() {
        if (FlowieeUtil.ACCOUNT_USERNAME.equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.DELETE_DANHMUC.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.ACCOUNT_USERNAME);
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenImport() {
        if (FlowieeUtil.ACCOUNT_USERNAME.equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.IMPORT_DANHMUC.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.ACCOUNT_USERNAME);
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean kiemTraQuyenExport() {
        if (FlowieeUtil.ACCOUNT_USERNAME.equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.EXPORT_DANHMUC.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.ACCOUNT_USERNAME);
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}