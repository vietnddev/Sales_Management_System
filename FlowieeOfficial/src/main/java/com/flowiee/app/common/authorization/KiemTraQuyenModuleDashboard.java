package com.flowiee.app.common.authorization;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.RoleService;
import com.flowiee.app.hethong.model.action.DashboardAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModuleDashboard {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.DASHBOARD.name();

    public boolean kiemTraQuyenXem() {
        if (FlowieeUtil.ACCOUNT_USERNAME.equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DashboardAction.READ_DASHBOARD.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.ACCOUNT_USERNAME);
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}