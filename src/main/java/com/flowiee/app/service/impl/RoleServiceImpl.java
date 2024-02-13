package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.AccountRole;
import com.flowiee.app.model.role.*;
import com.flowiee.app.repository.RoleRepository;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
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

        for (AppConstants.SYSTEM_MODULE sys : AppConstants.SYSTEM_MODULE.values()) {
            Role response = new Role();
            Map<String, String> module = new LinkedHashMap<>();
            module.put(sys.name(),sys.getLabel());
            response.setModule(module);

            switch (sys) {
                case PRODUCT:
                    List<Role.Action> listActionProduct = new ArrayList<>();
                    for (AppConstants.PRODUCT_ACTION actionProduct : AppConstants.PRODUCT_ACTION.values()) {
                        listActionProduct.add(new Role.Action(actionProduct.name(), actionProduct.getLabel()));
                    }
                    response.setAction(listActionProduct);
                    break;
                case STORAGE:
                    List<Role.Action> listActionStaroge = new ArrayList<>();
                    for (AppConstants.STORAGE_ACTION actionStorage : AppConstants.STORAGE_ACTION.values()) {
                    	listActionStaroge.add(new Role.Action(actionStorage.name(), actionStorage.getLabel()));
                    }
                    response.setAction(listActionStaroge);
                    break;
                case CATEGORY:
                    List<Role.Action> listActionCategory = new ArrayList<>();
                    for (AppConstants.CATEGORY_ACTION actionCategory : AppConstants.CATEGORY_ACTION.values()) {
                    	listActionCategory.add(new Role.Action(actionCategory.name(), actionCategory.getLabel()));
                    }
                    response.setAction(listActionCategory);
                    break;
                case SYSTEM:
                    List<Role.Action> listActionSystem = new ArrayList<>();
                    for (AppConstants.SYSTEM_ACTION actionSystem : AppConstants.SYSTEM_ACTION.values()) {
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

        for (AppConstants.SYSTEM_MODULE sys : AppConstants.SYSTEM_MODULE.values()) {
            switch (sys) {  
                case PRODUCT:
                    for (AppConstants.PRODUCT_ACTION actions : AppConstants.PRODUCT_ACTION.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case STORAGE:
                    for (AppConstants.STORAGE_ACTION actions : AppConstants.STORAGE_ACTION.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case SYSTEM:
                    for (AppConstants.SYSTEM_ACTION actions : AppConstants.SYSTEM_ACTION.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case CATEGORY:
                    for (AppConstants.CATEGORY_ACTION actions : AppConstants.CATEGORY_ACTION.values()) {
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
        for (AppConstants.SYSTEM_MODULE sys : AppConstants.SYSTEM_MODULE.values()) {
            listModuleKey.add(sys.name());
        }
        return listModuleKey;
    }

    @Override
    public List<ModuleOfFlowiee> findAllModule() {
        List<ModuleOfFlowiee> listModule = new ArrayList<>();
        for (AppConstants.SYSTEM_MODULE sysModule : AppConstants.SYSTEM_MODULE.values()) {
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
        for (AppConstants.SYSTEM_MODULE sys : AppConstants.SYSTEM_MODULE.values()) {
            switch (sys) {
                case PRODUCT:
                    for (AppConstants.PRODUCT_ACTION actions : AppConstants.PRODUCT_ACTION.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case STORAGE:
                    for (AppConstants.STORAGE_ACTION actions : AppConstants.STORAGE_ACTION.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case SYSTEM:
                    for (AppConstants.SYSTEM_ACTION actions : AppConstants.SYSTEM_ACTION.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case CATEGORY:
                    for (AppConstants.CATEGORY_ACTION actions : AppConstants.CATEGORY_ACTION.values()) {
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
        for (AppConstants.SYSTEM_MODULE sysModule : AppConstants.SYSTEM_MODULE.values()) {
            switch (sysModule) {
                case PRODUCT:
                    for (AppConstants.PRODUCT_ACTION sysAction : AppConstants.PRODUCT_ACTION.values()) {
                        listAction.add(new ActionOfModule(sysAction.name(), sysAction.getLabel(), sysModule.name()));
                    }
                    break;
                case STORAGE:
                    for (AppConstants.STORAGE_ACTION sysAction : AppConstants.STORAGE_ACTION.values()) {
                    	listAction.add(new ActionOfModule(sysAction.name(), sysAction.getLabel(), sysModule.name()));
                    }
                    break;
                case SYSTEM:
                    for (AppConstants.SYSTEM_ACTION sysAction : AppConstants.SYSTEM_ACTION.values()) {
                    	listAction.add(new ActionOfModule(sysAction.name(), sysAction.getLabel(), sysModule.name()));
                    }
                    break;
                case CATEGORY:
                    for (AppConstants.CATEGORY_ACTION sysAction : AppConstants.CATEGORY_ACTION.values()) {
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
        return MessageUtils.DELETE_SUCCESS;
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