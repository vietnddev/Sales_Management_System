package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.model.role.*;
import com.flowiee.pms.repository.system.AccountRoleRepository;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.service.system.RoleService;

import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountRoleServiceImpl implements RoleService {
    AccountRepository     mvAccountRepository;
    GroupAccountService   mvGroupAccountService;
    AccountRoleRepository mvAccountRoleRepository;

    @Override
    public List<RoleModel> findAllRoleByAccountId(Integer accountId) {
        Optional<Account> account = mvAccountRepository.findById(accountId);
        if (account.isEmpty()) {
            return List.of();
        }
        List<RoleModel> listReturn = new ArrayList<>();
        for (ACTION act : ACTION.values()) {
            listReturn.add(initRoleModel(null, accountId, act.getModuleKey(), act.getModuleLabel(), act.name(), act.getActionLabel()));
        }
        return listReturn;
    }

    @Override
    public List<RoleModel> findAllRoleByGroupId(Integer groupId) {
        Optional<GroupAccount> groupAcc = mvGroupAccountService.findById(groupId);
        if (groupAcc.isEmpty()) {
            return List.of();
        }
        List<RoleModel> listReturn = new ArrayList<>();
        for (ACTION act : ACTION.values()) {
            listReturn.add(initRoleModel(groupId, null, act.getModuleKey(), act.getModuleLabel(), act.name(), act.getActionLabel()));
        }
        return listReturn;
    }

    @Override
    public List<ActionModel> findAllAction() {
        List<ActionModel> listAction = new ArrayList<>();
        for (ACTION sysAction : ACTION.values()) {
            listAction.add(ActionModel.builder()
                    .actionKey(sysAction.getActionKey())
                    .actionLabel(sysAction.getActionLabel())
                    .moduleKey(sysAction.getModuleKey())
                    .build());
        }
        return listAction;
    }

    @Override
    public AccountRole findById(Integer id) {
        return mvAccountRoleRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccountRole> findByAccountId(Integer accountId) {
        return mvAccountRoleRepository.findByAccountId(accountId);
    }

    @Override
    public List<AccountRole> findByGroupId(Integer accountId) {
        return mvAccountRoleRepository.findByGroupId(accountId);
    }

    @Override
    public String updatePermission(String moduleKey, String actionKey, Integer accountId) {
        mvAccountRoleRepository.save(AccountRole.builder()
                .module(moduleKey)
                .action(actionKey)
                .accountId(accountId)
                .build());
        return MessageCode.UPDATE_SUCCESS.getDescription();
    }

    @Override
    public boolean isAuthorized(int accountId, String module, String action) {
        return mvAccountRoleRepository.isAuthorized(null, accountId, module, action) != null;
    }

    @Override
    public String deleteAllRole(Integer groupId, Integer accountId) {
        if (groupId == null && accountId == null) {
            throw new IllegalArgumentException("groupId and accountId cannot be null");
        }
        mvAccountRoleRepository.deleteAll(groupId, accountId);
        return MessageCode.DELETE_SUCCESS.getDescription();
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
            mvAccountRoleRepository.save(AccountRole.builder()
                    .module(role.getModule().getModuleKey())
                    .action(role.getAction().getActionKey())
                    .groupId(groupId)
                    .build());
            list.add(role);
        }
        return list;
    }

    @Override
    public List<AccountRole> findByAction(ACTION action) {
        return mvAccountRoleRepository.findByModuleAndAction(action.getModuleKey(), action.getActionKey());
    }

    private RoleModel initRoleModel(Integer pGroupId, Integer pAccountId, String pModuleKey, String pModuleLabel, String pActionKey, String pActionLabel) {
        return RoleModel.builder()
                .module(ModuleModel.builder().moduleKey(pModuleKey).moduleLabel(pModuleLabel).build())
                .action(ActionModel.builder().moduleKey(pModuleKey).actionKey(pActionKey).actionLabel(pActionLabel).build())
                .isAuthor((mvAccountRoleRepository.isAuthorized(pGroupId, pAccountId, pModuleKey, pActionKey)) != null)
                .groupId(pGroupId)
                .accountId(pAccountId)
                .build();
    }
}