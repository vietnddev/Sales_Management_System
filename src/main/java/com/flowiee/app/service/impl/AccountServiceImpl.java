package com.flowiee.app.service.impl;

import com.flowiee.app.exception.AppException;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.model.role.Role;
import com.flowiee.app.repository.AccountRepository;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.service.SystemLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
    @Autowired
    AccountRepository accountRepo;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private RoleService roleService;

    @Override
    public List<Account> findAll() {
        List<Account> listAccountReturn = new ArrayList<>();
        for (Account account : accountRepo.findAll()) {
            account.setListRole(roleService.findAllRole());
            for (Role role : account.getListRole()) {
                String module = role.getModule().keySet().toString().replaceAll("\\[|\\]", "").replaceAll("\"", "");
                if (role.getAction() != null) {
                    for (Role.Action act : role.getAction()) {
                        if (roleService.isAuthorized(account.getId(), module, act.getKeyAction())) {
                            act.setChecked(true);
                        }
                    }
                }
            }
            listAccountReturn.add(account);
        }
        return listAccountReturn;
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepo.findByUsername(username);
    }

    @Override
    public Account findById(Integer accountId) {
        return accountRepo.findById(accountId).orElse(null);
    }

    @Override
    public Account findCurrentAccount() {
        return this.findById(CommonUtils.getCurrentAccountId());
    }

    @Override
    public Account save(Account account) {
    	try {
            if (account.getRole() != null && account.getRole().equals(CommonUtils.ADMINISTRATOR)) {
                account.setRole("ADMIN");
            } else {
                account.setRole("USER");
            }
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            String password = account.getPassword();
            account.setPassword(bCrypt.encode(password));
            Account accountSaved = accountRepo.save(account);
        	SystemLog systemLog = new SystemLog(AppConstants.SYSTEM_MODULE.SYSTEM.name(), AppConstants.SYSTEM_ACTION.SYS_ACCOUNT_CREATE.name(), "Thêm mới account: " + account.getUsername(), null, CommonUtils.getCurrentAccountId(), CommonUtils.getCurrentAccountIp());
            systemLogService.writeLog(systemLog);
            logger.info("Insert account success! username=" + account.getUsername());
            return accountSaved;
		} catch (Exception e) {
			logger.error("Insert account fail! username=" + account.getUsername(), e);
			throw new AppException();
		}
    }

    @Transactional
    @Override
    public Account update(Account account, Integer entityId) {
    	try {
            account.setId(entityId);
            if (account.getRole() != null && account.getRole().equals(CommonUtils.ADMINISTRATOR)) {
                account.setRole("ADMIN");
            } else {
                account.setRole("USER");
            }
        	SystemLog systemLog = new SystemLog(AppConstants.SYSTEM_MODULE.SYSTEM.name(), AppConstants.SYSTEM_ACTION.SYS_ACCOUNT_UPDATE.name(), "Cập nhật account: " + account.getUsername(), null, CommonUtils.getCurrentAccountId(), CommonUtils.getCurrentAccountIp());
            systemLogService.writeLog(systemLog);
            logger.info("Update account success! username=" + account.getUsername());
            return accountRepo.save(account);
		} catch (Exception e) {
			logger.error("Update account fail! username=" + account.getUsername(), e);
            throw new AppException();
		}
    }

    @Transactional
    @Override
    public String delete(Integer accountId) {
    	Account account = null;
    	try {
            account = accountRepo.findById(accountId).orElse(null);
            if (account != null) {
                accountRepo.delete(account);
                SystemLog systemLog = new SystemLog(AppConstants.SYSTEM_MODULE.SYSTEM.name(), AppConstants.SYSTEM_ACTION.SYS_ACCOUNT_DELETE.name(), "Xóa account " + account.getUsername(), null, CommonUtils.getCurrentAccountId(), CommonUtils.getCurrentAccountIp());
                systemLogService.writeLog(systemLog);
            }
            logger.info("Delete account success! username=" + account.getUsername());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
		} catch (Exception e) {
			logger.error("Delete account fail! username=" + account.getUsername(), e);
			return AppConstants.SERVICE_RESPONSE_FAIL;
		}
    }
}