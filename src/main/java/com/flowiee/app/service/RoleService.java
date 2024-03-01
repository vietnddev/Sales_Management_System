package com.flowiee.app.service;

import com.flowiee.app.entity.AccountRole;
import com.flowiee.app.model.role.ActionModel;
import com.flowiee.app.model.role.FlowieeRole;
import com.flowiee.app.model.role.Role;

import java.util.List;

public interface RoleService {
    List<FlowieeRole> findAllRoleByAccountId(Integer accountId);

    List<ActionModel> findAllAction();

    AccountRole findById(Integer id);

    List<AccountRole> findByAccountId(Integer accountId);

    String updatePermission(String moduleKey, String actionKey, Integer accountId);

    boolean isAuthorized(int accountId, String module, String action);

    String deleteAllRole(Integer accountId);
}