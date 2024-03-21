package com.flowiee.sms.service.impl;

import com.flowiee.sms.core.exception.AppException;
import com.flowiee.sms.model.ACTION;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.utils.CommonUtils;
import com.flowiee.sms.entity.Account;
import com.flowiee.sms.entity.SystemLog;
import com.flowiee.sms.repository.AccountRepository;
import com.flowiee.sms.service.AccountService;
import com.flowiee.sms.service.SystemLogService;

import com.flowiee.sms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired private AccountRepository accountRepo;
    @Autowired private SystemLogService systemLogService;

    @Override
    public List<Account> findAll() {
        return accountRepo.findAll();
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
        return this.findById(CommonUtils.getUserPrincipal().getId());
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
        	SystemLog systemLog = new SystemLog(MODULE.SYSTEM.name(), ACTION.SYS_ACCOUNT_CREATE.name(), "Thêm mới account: " + account.getUsername(), null, CommonUtils.getUserPrincipal().getId(), CommonUtils.getUserPrincipal().getIp());
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
        	SystemLog systemLog = new SystemLog(MODULE.SYSTEM.name(), ACTION.SYS_ACCOUNT_UPDATE.name(), "Cập nhật account: " + account.getUsername(), null, CommonUtils.getUserPrincipal().getId(), CommonUtils.getUserPrincipal().getIp());
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
                SystemLog systemLog = new SystemLog(MODULE.SYSTEM.name(), ACTION.SYS_ACCOUNT_DELETE.name(), "Xóa account " + account.getUsername(), null, CommonUtils.getUserPrincipal().getId(), CommonUtils.getUserPrincipal().getIp());
                systemLogService.writeLog(systemLog);
            }
            logger.info("Delete account success! username=" + account.getUsername());
            return MessageUtils.DELETE_SUCCESS;
		} catch (Exception e) {
			logger.error("Delete account fail! username=" + account.getUsername(), e);
			throw new AppException();
		}
    }
}