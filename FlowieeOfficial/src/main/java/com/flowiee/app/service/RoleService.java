package com.flowiee.app.service;

import com.flowiee.app.entity.AccountRole;
import com.flowiee.app.model.system.ActionOfModule;
import com.flowiee.app.model.system.FlowieeRole;
import com.flowiee.app.model.system.ModuleOfFlowiee;
import com.flowiee.app.model.system.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllRole();

    List<FlowieeRole> findAllRoleByAccountId(Integer accountId);

    List<String> findAllModuleKey();

    List<ModuleOfFlowiee> findAllModule();

    List<String> findAllActionKey();

    List<ActionOfModule> findAllAction();

    AccountRole findById(Integer id);

    List<AccountRole> findByAccountId(Integer accountId);

    List<AccountRole> findByModule(String module);

    String updatePermission(String moduleKey, String actionKey, Integer accountId);

    boolean isAuthorized(int accountId, String module, String action);

    AccountRole findByActionAndAccountId(String action, Integer accountId);

    String deleteAllRole(Integer accountId);
}