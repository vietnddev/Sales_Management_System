package com.flowiee.app.base.vld;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.utils.AppConstants;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleStorage extends BaseAuthorize {
    public boolean dashboard(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DASHBOARD.name(), throwException);
    }

    public boolean readDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_READ.name(), throwException);
    }

    public boolean insertDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_CREATE.name(), throwException);
    }

    public boolean updateDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_UPDATE.name(), throwException);
    }

    public boolean deleteDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_DELETE.name(), throwException);
    }

    public boolean moveDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_MOVE.name(), throwException);
    }

    public boolean copyDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_COPY.name(), throwException);
    }

    public boolean downloadDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_DOWNLOAD.name(), throwException);
    }

    public boolean shareDoc(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_DOC_SHARE.name(), throwException);
    }

    public boolean readMaterial(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_MATERIAL_READ.name(), throwException);
    }

    public boolean insertMaterial(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_MATERIAL_CREATE.name(), throwException);
    }

    public boolean updateMaterial(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_MATERIAL_UPDATE.name(), throwException);
    }

    public boolean deleteMaterial(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_MATERIAL_DELETE.name(), throwException);
    }

    public boolean importGoods(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_TICKET_IMPORT_GOODS.name(), throwException);
    }

    public boolean exportGoods(boolean throwException) {
        return isAuthorized(AppConstants.STORAGE_ACTION.STG_TICKET_EXPORT_GOODS.name(), throwException);
    }
}