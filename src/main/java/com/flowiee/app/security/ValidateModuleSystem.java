package com.flowiee.app.security;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.model.role.SystemAction.SysAction;
import com.flowiee.app.model.role.LogAction;
import com.flowiee.app.model.role.RoleAction;
import com.flowiee.app.model.role.SystemModule;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleSystem extends BaseAuthorize {
    String module = SystemModule.SYSTEM.name();

    public boolean readPermission() {
        return isAuthorized(module, RoleAction.READ_ROLE.name());
    }

    public boolean readAccount() {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_READ.name());
    }

    public boolean insertAccount() {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_CREATE.name());
    }

    public boolean updateAccount() {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_UPDATE.name());
    }

    public boolean deleteAccount() {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_DELETE.name());
    }

    public boolean readLog() {
        return isAuthorized(module, LogAction.READ_LOG.name());
    }

    public boolean setupConfig() {
        return isAuthorized(module, "CONFIG");
    }
}