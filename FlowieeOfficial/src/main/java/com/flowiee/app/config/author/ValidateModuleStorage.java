package com.flowiee.app.config.author;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.service.system.RoleService;
import com.flowiee.app.common.action.KhoTaiLieuAction;
import com.flowiee.app.common.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateModuleStorage {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

    private final String module = SystemModule.KHO_TAI_LIEU.name();

    public boolean dashboard() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.DASHBOARD_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean read() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.READ_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean insert() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.CREATE_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean update() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.UPDATE_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean delete() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.DELETE_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean move() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.MOVE_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean copy() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.COPY_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean download() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.DOWNLOAD_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean share() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.SHARE_DOCUMENT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean material() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.MANAGEMENT_MATERIAL.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean goodsImport() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.MANAGEMENT_GOODS_DRAFT.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean goodsApprove() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.MANAGEMENT_GOODS_APPROVE.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }

    public boolean exportGoods() {
        if (accountService.findCurrentAccountUsername().equals(FlowieeUtil.ADMINISTRATOR)) {
            return true;
        }
        final String action = KhoTaiLieuAction.MANAGEMENT_GOODS_APPROVE.name();
        int accountId = accountService.findIdByUsername(accountService.findCurrentAccountUsername());
        if (roleService.isAuthorized(accountId, module, action)) {
            return true;
        }
        return false;
    }
}