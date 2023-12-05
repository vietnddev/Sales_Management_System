package com.flowiee.app.security.author;

import com.flowiee.app.model.role.SystemAction.SysAction;
import com.flowiee.app.model.role.LogAction;
import com.flowiee.app.model.role.RoleAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleSystem {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.SYSTEM.name();

    public boolean readPermission() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = RoleAction.READ_ROLE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readAccount() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SysAction.SYS_ACCOUNT_READ.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertAccount() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SysAction.SYS_ACCOUNT_CREATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateAccount() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SysAction.SYS_ACCOUNT_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteAccount() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = SysAction.SYS_ACCOUNT_DELETE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readLog() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = LogAction.READ_LOG.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean setupConfig() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = "CONFIG";
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}
