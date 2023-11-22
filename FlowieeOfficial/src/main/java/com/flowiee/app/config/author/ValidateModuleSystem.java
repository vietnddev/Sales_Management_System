package com.flowiee.app.config.author;

import com.flowiee.app.model.system.AccountAction;
import com.flowiee.app.model.system.LogAction;
import com.flowiee.app.model.system.RoleAction;
import com.flowiee.app.model.system.SystemModule;
import com.flowiee.app.utils.FlowieeUtil;
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

    private final String module = SystemModule.HE_THONG.name();

    public boolean readPermission() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = RoleAction.READ_ROLE.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updatePermission() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = RoleAction.UPDATE_ROLE.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readAccount() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = AccountAction.READ_ACCOUNT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insertAccount() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = AccountAction.CREATE_ACCOUNT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean updateAccount() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = AccountAction.UPDATE_ACCOUNT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean deleteAccount() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = AccountAction.DELETE_ACCOUNT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean readLog() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = LogAction.READ_LOG.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean setupConfig() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = "CONFIG";
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}
