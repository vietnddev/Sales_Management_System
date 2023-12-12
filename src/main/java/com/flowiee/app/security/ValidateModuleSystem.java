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

    public boolean readPermission(boolean throwException) {
        return isAuthorized(module, RoleAction.READ_ROLE.name(), throwException);
    }

    public boolean readAccount(boolean throwException) {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_READ.name(), throwException);
    }

    public boolean insertAccount(boolean throwException) {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_CREATE.name(), throwException);
    }

    public boolean updateAccount(boolean throwException) {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_UPDATE.name(), throwException);
    }

    public boolean deleteAccount(boolean throwException) {
        return isAuthorized(module, SysAction.SYS_ACCOUNT_DELETE.name(), throwException);
    }

    public boolean readLog(boolean throwException) {
        return isAuthorized(module, LogAction.READ_LOG.name(), throwException);
    }

    public boolean setupConfig(boolean throwException) {
        return isAuthorized(module, "CONFIG", throwException);
    }
}