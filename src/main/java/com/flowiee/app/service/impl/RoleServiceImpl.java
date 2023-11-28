package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.AccountRole;
import com.flowiee.app.model.role.*;
import com.flowiee.app.model.role.SystemAction.CategoryAction;
import com.flowiee.app.model.role.SystemAction.DashboardAction;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemAction.StorageAction;
import com.flowiee.app.model.role.SystemAction.SysAction;
import com.flowiee.app.repository.RoleRepository;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public List<Role> findAllRole() {
        List<Role> listReturn = new ArrayList<>();

        for (SystemModule sys : SystemModule.values()) {
            Role response = new Role();
            Map<String, String> module = new LinkedHashMap<>();
            module.put(sys.name(),sys.getLabel());
            response.setModule(module);

            switch (sys) {
                case PRODUCT:
                    List<Role.Action> listActionProduct = new ArrayList<>();
                    for (ProductAction actionProduct : ProductAction.values()) {
                        listActionProduct.add(new Role.Action(actionProduct.name(), actionProduct.getLabel()));
                    }
                    response.setAction(listActionProduct);
                    break;
                case STORAGE:
                    List<Role.Action> listActionStaroge = new ArrayList<>();
                    for (StorageAction actionStorage : StorageAction.values()) {
                    	listActionStaroge.add(new Role.Action(actionStorage.name(), actionStorage.getLabel()));
                    }
                    response.setAction(listActionStaroge);
                    break;
                case CATEGORY:
                    List<Role.Action> listActionCategory = new ArrayList<>();
                    for (CategoryAction actionCategory : CategoryAction.values()) {
                    	listActionCategory.add(new Role.Action(actionCategory.name(), actionCategory.getLabel()));
                    }
                    response.setAction(listActionCategory);
                    break;
                case SYSTEM:
                    List<Role.Action> listActionSystem = new ArrayList<>();
                    for (SysAction actionSystem : SysAction.values()) {
                    	listActionSystem.add(new Role.Action(actionSystem.name(), actionSystem.getLabel()));
                    }
                    response.setAction(listActionSystem);
                    break;
			default:
				break;
            }
            listReturn.add(response);
        }
        return listReturn;
    }

    @Override
    public List<FlowieeRole> findAllRoleByAccountId(Integer accountId) {
        Account account = accountService.findById(accountId);
        if (account == null) {
            return null;
        }
        List<FlowieeRole> listReturn = new ArrayList<>();

        for (SystemModule sys : SystemModule.values()) {
            switch (sys) {  
                case PRODUCT:
                    for (ProductAction actions : ProductAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case STORAGE:
                    for (StorageAction actions : StorageAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case SYSTEM:
                    for (SysAction actions : SysAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case CATEGORY:
                    for (CategoryAction actions : CategoryAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
            }
        }
        return listReturn;
    }

    @Override
    public List<String> findAllModuleKey() {
        List<String> listModuleKey = new ArrayList<>();
        for (SystemModule sys : SystemModule.values()) {
            listModuleKey.add(sys.name());
        }
        return listModuleKey;
    }

    @Override
    public List<ModuleOfFlowiee> findAllModule() {
        List<ModuleOfFlowiee> listModule = new ArrayList<>();
        for (SystemModule sysModule : SystemModule.values()) {
            ModuleOfFlowiee moduleOfFlowiee = new ModuleOfFlowiee();
            moduleOfFlowiee.setModuleKey(sysModule.name());
            moduleOfFlowiee.setModuleLabel(sysModule.getLabel());
            listModule.add(moduleOfFlowiee);
        }
        return listModule;
    }

    @Override
    public List<String> findAllActionKey() {
        List<String> listActionKey = new ArrayList<>();
        for (SystemModule sys : SystemModule.values()) {
            switch (sys) {
                case PRODUCT:
                    for (ProductAction actions : ProductAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case STORAGE:
                    for (StorageAction actions : StorageAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case SYSTEM:
                    for (SysAction actions : SysAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case CATEGORY:
                    for (CategoryAction actions : CategoryAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
            }
        }
        return listActionKey;
    }

    @Override
    public List<ActionOfModule> findAllAction() {
        List<ActionOfModule> listAction = new ArrayList<>();
        for (SystemModule sysModule : SystemModule.values()) {
            switch (sysModule) {
                case PRODUCT:
                    for (ProductAction sysAction : ProductAction.values()) {
                        listAction.add(new ActionOfModule(sysAction.name(), sysAction.getLabel(), sysModule.name()));
                    }
                    break;
                case STORAGE:
                    for (StorageAction sysAction : StorageAction.values()) {
                    	listAction.add(new ActionOfModule(sysAction.name(), sysAction.getLabel(), sysModule.name()));
                    }
                    break;
                case SYSTEM:
                    for (SysAction sysAction : SysAction.values()) {
                    	listAction.add(new ActionOfModule(sysAction.name(), sysAction.getLabel(), sysModule.name()));
                    }
                    break;
                case CATEGORY:
                    for (CategoryAction sysAction : CategoryAction.values()) {
                    	listAction.add(new ActionOfModule(sysAction.name(), sysAction.getLabel(), sysModule.name()));
                    }
                    break;
            }
        }
        return listAction;
    }

    @Override
    public AccountRole findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccountRole> findByAccountId(Integer accountId) {
        return roleRepository.findByAccountId(accountId);
    }

    @Override
    public List<AccountRole> findByModule(String module) {
        return roleRepository.findByModule(module);
    }

    @Override
    public String updatePermission(String moduleKey, String actionKey, Integer accountId) {
        AccountRole accountRole = new AccountRole();
        accountRole.setModule(moduleKey);
        accountRole.setAction(actionKey);
        accountRole.setAccountId(accountId);
        roleRepository.save(accountRole);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public boolean isAuthorized(int accountId, String module, String action) {
        if (roleRepository.isAuthorized(accountId, module, action) != null) {
            return true;
        }
        return false;
    }

    @Override
    public AccountRole findByActionAndAccountId(String action, Integer accoutnId) {
        return roleRepository.findByActionAndAccountId(action, accoutnId);
    }

    @Override
    public String deleteAllRole(Integer accountId) {
        roleRepository.deleteByAccountId(accountId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    private FlowieeRole buildFlowieeRole(Integer pAccountId, String pModuleKey, String pModuleLabel, String pActionKey, String pActionLabel) {
        FlowieeRole flowieeRole = new FlowieeRole();

        ModuleOfFlowiee module = new ModuleOfFlowiee();
        module.setModuleKey(pModuleKey);
        module.setModuleLabel(pModuleLabel);
        flowieeRole.setModule(module);

        ActionOfModule action = new ActionOfModule();
        action.setActionKey(pActionKey);
        action.setActionLabel(pActionLabel);
        action.setModuleKey(pModuleKey);
        flowieeRole.setAction(action);

        AccountRole isAuthor = roleRepository.isAuthorized(pAccountId, pModuleKey, pActionKey);
        flowieeRole.setIsAuthor(isAuthor != null ? true : false);

        flowieeRole.setAccountId(pAccountId);

        return flowieeRole;
    }
}