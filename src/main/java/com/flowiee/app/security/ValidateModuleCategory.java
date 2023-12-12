package com.flowiee.app.security;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.model.role.SystemAction.CategoryAction;
import com.flowiee.app.model.role.SystemModule;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleCategory extends BaseAuthorize {
    String module = SystemModule.CATEGORY.name();

    public boolean readCategory(boolean throwException) {
        return isAuthorized(module, CategoryAction.CTG_READ.name(), throwException);
    }

    public boolean insertCategory(boolean throwException) {
        return isAuthorized(module, CategoryAction.CTG_CREATE.name(), throwException);
    }

    public boolean updateCategory(boolean throwException) {
        return isAuthorized(module, CategoryAction.CTG_UPDATE.name(), throwException);
    }

    public boolean deleteCategory(boolean throwException) {
        return isAuthorized(module, CategoryAction.CTG_DELETE.name(), throwException);
    }

    public boolean importCategory(boolean throwException) {
        return isAuthorized(module, CategoryAction.CTG_IMPORT.name(), throwException);
    }
}