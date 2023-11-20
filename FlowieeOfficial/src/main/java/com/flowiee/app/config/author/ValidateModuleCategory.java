package com.flowiee.app.config.author;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.service.system.RoleService;
import com.flowiee.app.common.action.DanhMucAction;
import com.flowiee.app.common.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateModuleCategory {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.DANH_MUC.name();

    public boolean readCategory() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.READ_DANHMUC.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertCategory() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.CREATE_DANHMUC.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateCategory() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.UPDATE_DANHMUC.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteCategory() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.DELETE_DANHMUC.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean importCategory() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DanhMucAction.IMPORT_DANHMUC.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}