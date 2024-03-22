package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.role.*;
import com.flowiee.pms.repository.system.AccountRoleRepository;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.RoleService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountRoleServiceImpl implements RoleService {
    private final AccountRoleRepository accountRoleRepo;
    private final AccountService accountService;

    @Autowired
    public AccountRoleServiceImpl(AccountRoleRepository accountRoleRepo, AccountService accountService) {
        this.accountRoleRepo = accountRoleRepo;
        this.accountService = accountService;
    }

    @Override
    public List<RoleModel> findAllRoleByAccountId(Integer accountId) {
        Optional<Account> account = accountService.findById(accountId);
        if (account.isEmpty()) {
            return new ArrayList<>();
        }
        List<RoleModel> listReturn = new ArrayList<>();
        for (ACTION act : ACTION.values()) {
            listReturn.add(buildFlowieeRole(accountId, act.getModuleKey(), act.getModuleLabel(), act.name(), act.getActionLabel()));
        }
        return listReturn;
    }

    @Override
    public List<ActionModel> findAllAction() {
        List<ActionModel> listAction = new ArrayList<>();
        for (ACTION sysAction : ACTION.values()) {
            listAction.add(new ActionModel(sysAction.getActionKey(), sysAction.getActionLabel(), sysAction.getModuleKey()));
        }
        return listAction;
    }

    @Override
    public AccountRole findById(Integer id) {
        return accountRoleRepo.findById(id).orElse(null);
    }

    @Override
    public List<AccountRole> findByAccountId(Integer accountId) {
        return accountRoleRepo.findByAccountId(accountId);
    }

    @Override
    public String updatePermission(String moduleKey, String actionKey, Integer accountId) {
        accountRoleRepo.save(new AccountRole(moduleKey, actionKey, accountId));
        return MessageUtils.UPDATE_SUCCESS;
    }

    @Override
    public boolean isAuthorized(int accountId, String module, String action) {
        return accountRoleRepo.isAuthorized(accountId, module, action) != null;
    }

    @Override
    public String deleteAllRole(Integer accountId) {
        accountRoleRepo.deleteByAccountId(accountId);
        return MessageUtils.DELETE_SUCCESS;
    }

    private RoleModel buildFlowieeRole(Integer pAccountId, String pModuleKey, String pModuleLabel, String pActionKey, String pActionLabel) {
        RoleModel roleModel = new RoleModel();

        ModuleModel module = new ModuleModel();
        module.setModuleKey(pModuleKey);
        module.setModuleLabel(pModuleLabel);
        roleModel.setModule(module);

        ActionModel action = new ActionModel();
        action.setActionKey(pActionKey);
        action.setActionLabel(pActionLabel);
        action.setModuleKey(pModuleKey);
        roleModel.setAction(action);

        AccountRole isAuthor = accountRoleRepo.isAuthorized(pAccountId, pModuleKey, pActionKey);
        roleModel.setIsAuthor(isAuthor != null);

        roleModel.setAccountId(pAccountId);

        return roleModel;
    }
}