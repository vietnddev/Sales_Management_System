package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.hethong.entity.AccountRole;
import com.flowiee.app.hethong.model.Role;
import com.flowiee.app.hethong.model.RoleResponse;
import com.flowiee.app.hethong.model.action.*;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.repository.RoleRepository;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.RoleService;
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
                case DASHBOARD:
                    List<Role.Action> actionDashboard = new ArrayList<>();
                    for (DashboardAction action : DashboardAction.values()) {
                        Role.Action ac = new Role.Action();
                        ac.setKeyAction(action.name());
                        ac.setValueAction(action.getLabel());
                        actionDashboard.add(ac);
                    }
                    response.setAction(actionDashboard);
                    break;
                case SAN_PHAM:
                    List<Role.Action> listActionSanPham = new ArrayList<>();
                    for (SanPhamAction actionSanPham : SanPhamAction.values()) {
                        Role.Action action = new Role.Action();
                        action.setKeyAction(actionSanPham.name());
                        action.setValueAction(actionSanPham.getLabel());
                        listActionSanPham.add(action);
                    }
                    response.setAction(listActionSanPham);
                    break;
                case KHO_TAI_LIEU:
                    List<Role.Action> listActionKhoTaiLieu = new ArrayList<>();
                    for (KhoTaiLieuAction actionKhoTaiLieu : KhoTaiLieuAction.values()) {
                        Role.Action action = new Role.Action();
                        action.setKeyAction(actionKhoTaiLieu.name());
                        action.setValueAction(actionKhoTaiLieu.getLabel());
                        listActionKhoTaiLieu.add(action);
                    }
                    response.setAction(listActionKhoTaiLieu);
                    break;
                case DANH_MUC:
                    List<Role.Action> listActionDanhMuc = new ArrayList<>();
                    for (DanhMucAction actionDanhMuc : DanhMucAction.values()) {
                        Role.Action action = new Role.Action();
                        action.setKeyAction(actionDanhMuc.name());
                        action.setValueAction(actionDanhMuc.getLabel());
                        listActionDanhMuc.add(action);
                    }
                    response.setAction(listActionDanhMuc);
                    break;
                case HE_THONG:
                    List<Role.Action> actionSystem = new ArrayList<>();
                    for (AccountAction actionAccount : AccountAction.values()) {
                        Role.Action action = new Role.Action();
                        action.setKeyAction(actionAccount.name());
                        action.setValueAction(actionAccount.getLabel());
                        actionSystem.add(action);
                    }
                    response.setAction(actionSystem);
                    break;
            }
            listReturn.add(response);
        }
        return listReturn;
    }

    @Override
    public AccountRole findById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<AccountRole> findByModule(String module) {
        return roleRepository.findByModule(module);
    }

    @Override
    public String save(AccountRole accountRole) {
        if (accountRole == null) {
            return "Null value!";
        }
        if (accountRole.getAccountId() <= 0 || accountService.findById(accountRole.getId()) == null) {
            return "Account invalid!";
        }
        if (accountRole.getModule().isEmpty() || accountRole.getAction().isEmpty()) {
            return "Module or Action invalid!";
        }
        if (!isAuthorized(accountRole.getAccountId(), accountRole.getModule(), accountRole.getAction())) {
            roleRepository.save(accountRole);
            return "OK";
        }
        return "NOK";
    }

    @Override
    public String update(AccountRole accountRole) {
        if (accountRole == null) {
            return "Null value!";
        }
        if (accountRole.getAccountId() <= 0 || accountService.findById(accountRole.getId()) == null) {
            return "Account invalid!";
        }
        if (accountRole.getModule().isEmpty() || accountRole.getAction().isEmpty()) {
            return "Module or Action invalid!";
        }
        if (accountRole.getId() <= 0 || findById(accountRole.getId()) == null) {
            return "Role invalid!";
        }
        if (isAuthorized(accountRole.getAccountId(), accountRole.getModule(), accountRole.getAction())) {
            roleRepository.save(accountRole);
            return "OK";
        }
        return "NOK";
    }

    @Override
    public String delete(int id) {
        if (id <= 0) {
            return "Id invalid!";
        }
        roleRepository.deleteById(id);
        return "OK";
    }

    @Override
    public boolean isAuthorized(int accountId, String module, String action) {
        if (roleRepository.isAuthorized(accountId, module, action) != null) {
            return true;
        }
        return false;
    }

//    @Override
//    public List<RoleResponse> convertToRoleResponse(List<Role> listRole) {
//        if (listRole == null) {
//            throw new BadRequestException();
//        }
//        List<RoleResponse> listReturn = new ArrayList<>();
//        for (Role role : listRole) {
//            RoleResponse roleResponse = new RoleResponse();
//            roleResponse.setModule(role.getModule());
//            roleResponse.setAction(role.getAction());
//            listReturn.add(roleResponse);
//        }
//        return listReturn;
//    }
}