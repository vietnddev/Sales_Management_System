package com.flowiee.app.config.author;

import com.flowiee.app.utils.FlowieeUtil;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.model.system.DashboardAction;
import com.flowiee.app.model.system.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateModuleDashboard {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.DASHBOARD.name();

    public boolean readDashboard() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DashboardAction.READ_DASHBOARD.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}