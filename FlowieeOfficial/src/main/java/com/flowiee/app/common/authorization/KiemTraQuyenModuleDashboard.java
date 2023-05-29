package com.flowiee.app.common.authorization;

import com.flowiee.app.common.utils.TaiKhoanUtil;
import com.flowiee.app.account.service.AccountService;
import com.flowiee.app.role.service.AccountRoleService;
import com.flowiee.app.system.action.DashboardAction;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KiemTraQuyenModuleDashboard {
    @Autowired
    private AccountRoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.DASHBOARD.name();

    public boolean kiemTraQuyenXem() {
        if (accountService.getUserName().equals(TaiKhoanUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = DashboardAction.READ.name();
        int accountId = accountService.findIdByUsername(accountService.getUserName());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}