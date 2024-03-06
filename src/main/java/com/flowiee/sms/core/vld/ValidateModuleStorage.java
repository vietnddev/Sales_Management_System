package com.flowiee.sms.core.vld;

import com.flowiee.sms.core.BaseAuthorize;
import com.flowiee.sms.model.ACTION;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleStorage extends BaseAuthorize {
    public boolean dashboard(boolean throwException) {
        return isAuthorized(ACTION.STG_DASHBOARD.name(), throwException);
    }

    public boolean readMaterial(boolean throwException) {
        return isAuthorized(ACTION.STG_MATERIAL_READ.name(), throwException);
    }

    public boolean insertMaterial(boolean throwException) {
        return isAuthorized(ACTION.STG_MATERIAL_CREATE.name(), throwException);
    }

    public boolean updateMaterial(boolean throwException) {
        return isAuthorized(ACTION.STG_MATERIAL_UPDATE.name(), throwException);
    }

    public boolean deleteMaterial(boolean throwException) {
        return isAuthorized(ACTION.STG_MATERIAL_DELETE.name(), throwException);
    }

    public boolean importGoods(boolean throwException) {
        return isAuthorized(ACTION.STG_TICKET_IMPORT_GOODS.name(), throwException);
    }

    public boolean exportGoods(boolean throwException) {
        return isAuthorized(ACTION.STG_TICKET_EXPORT_GOODS.name(), throwException);
    }
}