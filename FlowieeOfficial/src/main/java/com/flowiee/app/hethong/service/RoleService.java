package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.AccountRole;
import com.flowiee.app.hethong.model.Role;
import com.flowiee.app.hethong.model.RoleResponse;

import java.util.List;

public interface RoleService {

    List<Role> findAllRole();

    AccountRole findById(int id);

    List<AccountRole> findByModule(String module);

    String save(AccountRole accountRoleEntity);

    String update(AccountRole accountRoleEntity);

    String delete(int id);

    boolean isAuthorized(int accountId, String module, String action);

    //List<RoleResponse> convertToRoleResponse(List<Role> listRole);
}