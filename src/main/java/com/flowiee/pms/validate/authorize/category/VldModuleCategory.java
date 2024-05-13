package com.flowiee.pms.validate.authorize.category;

import com.flowiee.pms.validate.authorize.BaseAuthorize;
import com.flowiee.pms.model.ACTION;
import org.springframework.stereotype.Component;

@Component
public class VldModuleCategory extends BaseAuthorize implements IVldModuleCategory {
    @Override
    public boolean readCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_R.name(), throwException);
    }

    @Override
    public boolean insertCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_C.name(), throwException);
    }

    @Override
    public boolean updateCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_U.name(), throwException);
    }

    @Override
    public boolean deleteCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_D.name(), throwException);
    }

    @Override
    public boolean importCategory(boolean throwException) {
        return super.isAuthorized(ACTION.CTG_I.name(), throwException);
    }
}