package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.entity.system.GroupAccount;
import com.flowiee.pms.exception.EntityNotFoundException;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.common.enumeration.ACTION;
import com.flowiee.pms.model.role.*;
import com.flowiee.pms.repository.system.AccountRoleRepository;
import com.flowiee.pms.security.UserRightsTemp;
import com.flowiee.pms.service.system.GroupAccountService;
import com.flowiee.pms.service.system.RoleService;

import com.flowiee.pms.common.enumeration.MessageCode;
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
    UserRightsTemp        mvUserRightsTemp;

    @Override
    public List<RoleModel> findAllRoleByAccountId(Long accountId) {
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
    public List<RoleModel> findAllRoleByGroupId(Long groupId) {
        GroupAccount groupAcc = mvGroupAccountService.findById(groupId, false);
        if (groupAcc == null) {
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
    public AccountRole findById(Long id, boolean pThrowException) {
        Optional<AccountRole> entityOptional = mvAccountRoleRepository.findById(id);
        if (entityOptional.isEmpty() && pThrowException) {
            throw new EntityNotFoundException(new Object[] {"role"}, null, null);
        }
        return entityOptional.orElse(null);
    }

    @Override
    public List<AccountRole> findByAccountId(Long accountId) {
        return mvAccountRoleRepository.findByAccountId(accountId);
    }

    @Override
    public List<AccountRole> findByGroupId(Long accountId) {
        return mvAccountRoleRepository.findByGroupId(accountId);
    }

    @Override
    public String updatePermission(Account pAccount, List<ActionModel> pActionModelList) {
        long lvAccountId = pAccount.getId();
        String lvUsername = pAccount.getUsername();

        this.deleteAllRole(null, lvAccountId);

        List<String> lvRightsList = new ArrayList<>();
        for (ActionModel lvRightModel : pActionModelList) {
            String lvRight = lvRightModel.getActionKey();
            mvAccountRoleRepository.save(AccountRole.builder()
                    .module(lvRightModel.getModuleKey())
                    .action(lvRight)
                    .accountId(lvAccountId)
                    .build());
            lvRightsList.add(lvRight);
        }
        mvUserRightsTemp.addTempRights(lvUsername, lvRightsList);

        return MessageCode.UPDATE_SUCCESS.getDescription();
    }

    @Override
    public boolean isAuthorized(long accountId, String module, String action) {
        return mvAccountRoleRepository.isAuthorized(null, accountId, module, action) != null;
    }

    @Override
    public String deleteAllRole(Long groupId, Long accountId) {
        if (groupId == null && accountId == null) {
            throw new IllegalArgumentException("groupId and accountId cannot be null");
        }
        mvAccountRoleRepository.deleteByAccountId(accountId);
        mvAccountRoleRepository.deleteByGroupAccountId(groupId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<RoleModel> updateRightsOfGroup(List<RoleModel> rights, Long groupId) {
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

    private RoleModel initRoleModel(Long pGroupId, Long pAccountId, String pModuleKey, String pModuleLabel, String pActionKey, String pActionLabel) {
        AccountRole lvAccountRole = null;
        try {
            lvAccountRole = mvAccountRoleRepository.isAuthorized(pGroupId, pAccountId, pModuleKey, pActionKey);
        } catch (RuntimeException ex) {
            System.out.println("pGroupId " + pGroupId);
            System.out.println("pAccountId " + pAccountId);
            System.out.println("pModuleKey " + pModuleKey);
            System.out.println("pActionKey " + pActionKey);
            ex.printStackTrace();
        }

        return RoleModel.builder()
                .module(ModuleModel.builder().moduleKey(pModuleKey).moduleLabel(pModuleLabel).build())
                .action(ActionModel.builder().moduleKey(pModuleKey).actionKey(pActionKey).actionLabel(pActionLabel).build())
                .isAuthor(lvAccountRole != null)
                .groupId(pGroupId)
                .accountId(pAccountId)
                .build();
    }
}