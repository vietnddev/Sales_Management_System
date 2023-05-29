package com.flowiee.app.role.service;

import com.flowiee.app.account.service.AccountService;
import com.flowiee.app.role.entity.AccountRoleEntity;
import com.flowiee.app.role.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountRoleService {
    @Autowired
    private AccountRoleRepository roleRepository;

    @Autowired
    private AccountService accountService;

    public AccountRoleEntity findById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<AccountRoleEntity> findByModule(String module) {
        return roleRepository.findByModule(module);
    }

    public String save(AccountRoleEntity roleEntity) {
        if (roleEntity == null) {
            return "Null value!";
        }
        if (roleEntity.getAccountId() <= 0 || accountService.getAccountByID(roleEntity.getId()) == null) {
            return "Account invalid!";
        }
        if (roleEntity.getModule().isEmpty() || roleEntity.getAction().isEmpty()) {
            return "Module or Action invalid!";
        }
        if (!isAuthorized(roleEntity.getAccountId(), roleEntity.getModule(), roleEntity.getAction())) {
            roleRepository.save(roleEntity);
            return "OK";
        }
        return "NOK";
    }

    public String update(AccountRoleEntity roleEntity) {
        if (roleEntity == null) {
            return "Null value!";
        }
        if (roleEntity.getAccountId() <= 0 || accountService.getAccountByID(roleEntity.getId()) == null) {
            return "Account invalid!";
        }
        if (roleEntity.getModule().isEmpty() || roleEntity.getAction().isEmpty()) {
            return "Module or Action invalid!";
        }
        if (roleEntity.getId() <= 0 || findById(roleEntity.getId()) == null) {
            return "Role invalid!";
        }
        if (isAuthorized(roleEntity.getAccountId(), roleEntity.getModule(), roleEntity.getAction())) {
            roleRepository.save(roleEntity);
            return "OK";
        }
        return "NOK";
    }

    public String delete(int id) {
        if (id <= 0) {
            return "Id invalid!";
        }
        roleRepository.deleteById(id);
        return "OK";
    }

    public boolean isAuthorized(int accountId, String module, String action) {
        if (roleRepository.isAuthorized(accountId, module, action) != null) {
            return true;
        }
        return false;
    }
}