package com.flowiee.app.security.author;

import com.flowiee.app.utils.FlowieeUtil;
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
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DASHBOARD.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean read() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_READ.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insert() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_CREATE.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean update() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_UPDATE.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean delete() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_DELETE.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean move() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_MOVE.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean copy() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_COPY.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean download() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_DOWNLOAD.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean share() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_DOC_SHARE.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean material() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_MATERIAL.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean importGoods() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_TICKET_IMPORT_GOODS.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean exportGoods() {
        if (FlowieeUtil.getCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = StorageAction.STG_TICKET_EXPORT_GOODS.name();
        int accountId = accountService.findIdByUsername(FlowieeUtil.getCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}