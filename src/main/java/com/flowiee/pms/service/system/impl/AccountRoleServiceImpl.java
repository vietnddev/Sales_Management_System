package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.role.*;
import com.flowiee.pms.repository.system.AccountRoleRepository;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.service.system.RoleService;

import com.flowiee.pms.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountRoleServiceImpl implements RoleService {
    private final AccountRoleRepository accountRoleRepo;
    private final AccountService accountService;
    private final GroupAccountService groupAccountService;

    @Autowired
    public AccountRoleServiceImpl(AccountRoleRepository accountRoleRepo, AccountService accountService, GroupAccountService groupAccountService) {
        this.accountRoleRepo = accountRoleRepo;
        this.accountService = accountService;
        this.groupAccountService = groupAccountService;
    }

    @Override
    public List<RoleModel> findAllRoleByAccountId(Integer accountId) {
        Optional<Account> account = accountService.findById(accountId);
        if (account.isEmpty()) {
            return List.of();
        }
        List<RoleModel> listReturn = new ArrayList<>();
        for (ACTION act : ACTION.values()) {
            listReturn.add(initRole(null, accountId, act.getModuleKey(), act.getModuleLabel(), act.name(), act.getActionLabel()));
        }
        return listReturn;
    }

    @Override
    public List<RoleModel> findAllRoleByGroupId(Integer groupId) {
        Optional<GroupAccount> groupAcc = groupAccountService.findById(groupId);
        if (groupAcc.isEmpty()) {
            return List.of();
        }
        List<RoleModel> listReturn = new ArrayList<>();
        for (ACTION act : ACTION.values()) {
            listReturn.add(initRole(groupId, null, act.getModuleKey(), act.getModuleLabel(), act.name(), act.getActionLabel()));
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
    public List<AccountRole> findByGroupId(Integer accountId) {
        return accountRoleRepo.findByGroupId(accountId);
    }

    @Override
    public String updatePermission(String moduleKey, String actionKey, Integer accountId) {
        accountRoleRepo.save(new AccountRole(moduleKey, actionKey, accountId, null));
        return MessageUtils.UPDATE_SUCCESS;
    }

    @Override
    public boolean isAuthorized(int accountId, String module, String action) {
        return accountRoleRepo.isAuthorized(null, accountId, module, action) != null;
    }

    @Override
    public String deleteAllRole(Integer groupId, Integer accountId) {
        if (groupId == null && accountId == null) {
            throw new IllegalArgumentException("groupId and accountId cannot be null");
        }
        accountRoleRepo.deleteAll(groupId, accountId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public List<RoleModel> updateRightsOfGroup(List<RoleModel> rights, Integer groupId) {
        this.deleteAllRole(groupId, null);
        List<RoleModel> list = new ArrayList<>();
        if (ObjectUtils.isEmpty(rights)) {
            return list;
        }
        for (RoleModel role : rights) {
            if (ObjectUtils.isEmpty(role)) {
                return list;
            }
            if (role.getIsAuthor() == null || !role.getIsAuthor()) {
                continue;
            }
            String moduleKey = role.getModule().getModuleKey();
            String actionKey = role.getAction().getActionKey();
            accountRoleRepo.save(new AccountRole(moduleKey, actionKey, null, groupId));
            list.add(role);
        }
        return list;
    }

    private RoleModel initRole(Integer pGroupId, Integer pAccountId, String pModuleKey, String pModuleLabel, String pActionKey, String pActionLabel) {
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

        AccountRole isAuthor = accountRoleRepo.isAuthorized(pGroupId, pAccountId, pModuleKey, pActionKey);
        roleModel.setIsAuthor(isAuthor != null);

        roleModel.setGroupId(pGroupId);
        roleModel.setAccountId(pAccountId);

        return roleModel;
    }
}