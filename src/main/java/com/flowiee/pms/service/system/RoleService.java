package com.flowiee.pms.service.system;

import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.model.role.ActionModel;
import com.flowiee.pms.model.role.RoleModel;

import java.util.List;

public interface RoleService {
    List<RoleModel> findAllRoleByAccountId(Integer accountId);

    List<RoleModel> findAllRoleByGroupId(Integer groupId);

    List<ActionModel> findAllAction();

    AccountRole findById(Integer id);

    List<AccountRole> findByAccountId(Integer accountId);

    List<AccountRole> findByGroupId(Integer accountId);

    String updatePermission(String moduleKey, String actionKey, Integer accountId);

    boolean isAuthorized(int accountId, String module, String action);

    String deleteAllRole(Integer groupId, Integer accountId);

    List<RoleModel> updateRightsOfGroup(List<RoleModel> rights, Integer groupId);

    List<AccountRole> findByAction(ACTION action);
}