package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.AccountRole;
import com.flowiee.app.model.system.ActionOfModule;
import com.flowiee.app.model.system.FlowieeRole;
import com.flowiee.app.model.system.ModuleOfFlowiee;
import com.flowiee.app.model.system.Role;
import com.flowiee.app.repository.RoleRepository;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.common.action.*;
import com.flowiee.app.common.module.SystemModule;

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
                    List<Role.Action> listActionSystem = new ArrayList<>();
                    for (AccountAction actionAccount : AccountAction.values()) {
                        Role.Action action = new Role.Action();
                        action.setKeyAction(actionAccount.name());
                        action.setValueAction(actionAccount.getLabel());
                        listActionSystem.add(action);
                    }
                    response.setAction(listActionSystem);
                    break;
                case THU_VIEN_ANH:
                    List<Role.Action> listActionAlbum = new ArrayList<>();
                    for (ThuVienAnhAction actionAlbum : ThuVienAnhAction.values()) {
                        Role.Action action = new Role.Action();
                        action.setKeyAction(actionAlbum.name());
                        action.setValueAction(actionAlbum.getLabel());
                        listActionAlbum.add(action);
                    }
                    response.setAction(listActionAlbum);
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
                case DASHBOARD:
                    for (DashboardAction actions : DashboardAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case SAN_PHAM:
                    for (SanPhamAction actions : SanPhamAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case KHO_TAI_LIEU:
                    for (KhoTaiLieuAction actions : KhoTaiLieuAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case DANH_MUC:
                    for (DanhMucAction actions : DanhMucAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case HE_THONG:
                    for (AccountAction actions : AccountAction.values()) {
                        listReturn.add(buildFlowieeRole(accountId, sys.name(), sys.getLabel(),actions.name(), actions.getLabel()));
                    }
                    break;
                case THU_VIEN_ANH:
                    for (ThuVienAnhAction actions : ThuVienAnhAction.values()) {
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
                case DASHBOARD:
                    for (DashboardAction actions : DashboardAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case SAN_PHAM:
                    for (SanPhamAction actions : SanPhamAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case KHO_TAI_LIEU:
                    for (KhoTaiLieuAction actions : KhoTaiLieuAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case DANH_MUC:
                    for (DanhMucAction actions : DanhMucAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case HE_THONG:
                    for (AccountAction actions : AccountAction.values()) {
                        listActionKey.add(actions.name());
                    }
                    break;
                case THU_VIEN_ANH:
                    for (ThuVienAnhAction actions : ThuVienAnhAction.values()) {
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
                case DASHBOARD:
                    for (DashboardAction sysAction : DashboardAction.values()) {
                        ActionOfModule actionOfModule = new ActionOfModule();
                        actionOfModule.setActionKey(sysAction.name());
                        actionOfModule.setActionLabel(sysAction.getLabel());
                        actionOfModule.setModuleKey(sysModule.name());
                        listAction.add(actionOfModule);
                    }
                    break;
                case SAN_PHAM:
                    for (SanPhamAction sysAction : SanPhamAction.values()) {
                        ActionOfModule actionOfModule = new ActionOfModule();
                        actionOfModule.setActionKey(sysAction.name());
                        actionOfModule.setActionLabel(sysAction.getLabel());
                        actionOfModule.setModuleKey(sysModule.name());
                        listAction.add(actionOfModule);
                    }
                    for (VoucherAction sysAction : VoucherAction.values()) {
                        ActionOfModule actionOfModule = new ActionOfModule();
                        actionOfModule.setActionKey(sysAction.name());
                        actionOfModule.setActionLabel(sysAction.getLabel());
                        actionOfModule.setModuleKey(sysModule.name());
                        listAction.add(actionOfModule);
                    }
                    break;
                case KHO_TAI_LIEU:
                    for (KhoTaiLieuAction sysAction : KhoTaiLieuAction.values()) {
                        ActionOfModule actionOfModule = new ActionOfModule();
                        actionOfModule.setActionKey(sysAction.name());
                        actionOfModule.setActionLabel(sysAction.getLabel());
                        actionOfModule.setModuleKey(sysModule.name());
                        listAction.add(actionOfModule);
                    }
                    break;
                case DANH_MUC:
                    for (DanhMucAction sysAction : DanhMucAction.values()) {
                        ActionOfModule actionOfModule = new ActionOfModule();
                        actionOfModule.setActionKey(sysAction.name());
                        actionOfModule.setActionLabel(sysAction.getLabel());
                        actionOfModule.setModuleKey(sysModule.name());
                        listAction.add(actionOfModule);
                    }
                    break;
                case HE_THONG:
                    for (AccountAction sysAction : AccountAction.values()) {
                        ActionOfModule actionOfModule = new ActionOfModule();
                        actionOfModule.setActionKey(sysAction.name());
                        actionOfModule.setActionLabel(sysAction.getLabel());
                        actionOfModule.setModuleKey(sysModule.name());
                        listAction.add(actionOfModule);
                    }
                    break;
                case THU_VIEN_ANH:
                    for (ThuVienAnhAction sysAction : ThuVienAnhAction.values()) {
                        ActionOfModule actionOfModule = new ActionOfModule();
                        actionOfModule.setActionKey(sysAction.name());
                        actionOfModule.setActionLabel(sysAction.getLabel());
                        actionOfModule.setModuleKey(sysModule.name());
                        listAction.add(actionOfModule);
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
        return TagName.SERVICE_RESPONSE_SUCCESS;
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
        return TagName.SERVICE_RESPONSE_SUCCESS;
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