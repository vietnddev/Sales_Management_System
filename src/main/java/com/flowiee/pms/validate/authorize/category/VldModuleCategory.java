package com.flowiee.pms.validate.authorize.category;

import com.flowiee.pms.base.BaseAuthorize;
import com.flowiee.pms.model.ACTION;
import org.springframework.stereotype.Component;

@Component
public class VldModuleCategory extends BaseAuthorize implements IVldModuleCategory {
    @Override
    public boolean readCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_READ.name(), throwException);
    }

    @Override
    public boolean insertCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_CREATE.name(), throwException);
    }

    @Override
    public boolean updateCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_UPDATE.name(), throwException);
    }

    @Override
    public boolean deleteCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_DELETE.name(), throwException);
    }

    @Override
    public boolean importCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_IMPORT.name(), throwException);
    }
}