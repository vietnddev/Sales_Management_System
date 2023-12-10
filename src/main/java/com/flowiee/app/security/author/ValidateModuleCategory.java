package com.flowiee.app.security.author;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.model.role.SystemAction.CategoryAction;
import com.flowiee.app.model.role.SystemModule;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleCategory extends BaseAuthorize {
    String module = SystemModule.CATEGORY.name();

    public boolean readCategory() {
        return isAuthorized(module, CategoryAction.CTG_READ.name());
    }

    public boolean insertCategory() {
        return isAuthorized(module, CategoryAction.CTG_CREATE.name());
    }

    public boolean updateCategory() {
        return isAuthorized(module, CategoryAction.CTG_UPDATE.name());
    }

    public boolean deleteCategory() {
        return isAuthorized(module, CategoryAction.CTG_DELETE.name());
    }

    public boolean importCategory() {
        return isAuthorized(module, CategoryAction.CTG_IMPORT.name());
    }
}