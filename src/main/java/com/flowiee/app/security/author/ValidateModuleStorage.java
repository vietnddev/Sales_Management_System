package com.flowiee.app.security.author;

import com.flowiee.app.base.BaseAuthorize;
import com.flowiee.app.model.role.SystemAction.StorageAction;
import com.flowiee.app.model.role.SystemModule;
import org.springframework.stereotype.Component;

@Component
public class ValidateModuleStorage extends BaseAuthorize {
     String module = SystemModule.STORAGE.name();

    public boolean dashboard() {
        return isAuthorized(module, StorageAction.STG_DASHBOARD.name());
    }

    public boolean read() {
        return isAuthorized(module, StorageAction.STG_DOC_READ.name());
    }

    public boolean insert() {
        return isAuthorized(module, StorageAction.STG_DOC_CREATE.name());
    }

    public boolean update() {
        return isAuthorized(module, StorageAction.STG_DOC_UPDATE.name());
    }

    public boolean delete() {
        return isAuthorized(module, StorageAction.STG_DOC_DELETE.name());
    }

    public boolean move() {
        return isAuthorized(module, StorageAction.STG_DOC_MOVE.name());
    }

    public boolean copy() {
        return isAuthorized(module, StorageAction.STG_DOC_COPY.name());
    }

    public boolean download() {
        return isAuthorized(module, StorageAction.STG_DOC_DOWNLOAD.name());
    }

    public boolean share() {
        return isAuthorized(module, StorageAction.STG_DOC_SHARE.name());
    }

    public boolean material() {
        return isAuthorized(module, StorageAction.STG_MATERIAL.name());
    }

    public boolean importGoods() {
        return isAuthorized(module, StorageAction.STG_TICKET_IMPORT_GOODS.name());
    }

    public boolean exportGoods() {
        return isAuthorized(module, StorageAction.STG_TICKET_EXPORT_GOODS.name());
    }
}