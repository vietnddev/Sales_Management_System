package com.flowiee.app.security;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.model.role.SystemAction.StorageAction;
import com.flowiee.app.model.role.SystemModule;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleStorage extends BaseAuthorize {
     String module = SystemModule.STORAGE.name();

    public boolean dashboard(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DASHBOARD.name(), throwException);
    }

    public boolean read(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_READ.name(), throwException);
    }

    public boolean insert(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_CREATE.name(), throwException);
    }

    public boolean update(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_UPDATE.name(), throwException);
    }

    public boolean delete(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_DELETE.name(), throwException);
    }

    public boolean move(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_MOVE.name(), throwException);
    }

    public boolean copy(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_COPY.name(), throwException);
    }

    public boolean download(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_DOWNLOAD.name(), throwException);
    }

    public boolean share(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_DOC_SHARE.name(), throwException);
    }

    public boolean material(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_MATERIAL.name(), throwException);
    }

    public boolean importGoods(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_TICKET_IMPORT_GOODS.name(), throwException);
    }

    public boolean exportGoods(boolean throwException) {
        return isAuthorized(module, StorageAction.STG_TICKET_EXPORT_GOODS.name(), throwException);
    }
}