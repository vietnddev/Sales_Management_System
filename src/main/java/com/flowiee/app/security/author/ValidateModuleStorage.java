package com.flowiee.app.security.author;

import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.model.role.SystemAction.StorageAction;
import com.flowiee.app.model.role.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateModuleStorage {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.STORAGE.name();

    public boolean dashboard() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DASHBOARD.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean read() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_READ.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insert() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_CREATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean update() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_UPDATE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean delete() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_DELETE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean move() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_MOVE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean copy() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_COPY.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean download() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_DOWNLOAD.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean share() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_SHARE.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean material() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_MATERIAL.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean importGoods() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_TICKET_IMPORT_GOODS.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean exportGoods() {
        if (CommonUtil.getCurrentAccountUsername().equals(CommonUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_TICKET_EXPORT_GOODS.name();
        int accountId = accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}