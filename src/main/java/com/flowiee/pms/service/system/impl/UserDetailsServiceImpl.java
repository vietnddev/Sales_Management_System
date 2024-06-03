package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.model.UserPrincipal;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.repository.system.SystemLogRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.RoleService;

import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserDetailsServiceImpl extends BaseService implements UserDetailsService, AccountService {
	private static final String mainObjectName = "Account";

	@Autowired
	private AccountRepository accountRepo;
	@Autowired @Lazy
	private RoleService roleService;
	@Autowired
	private SystemLogRepository systemLogRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account accountEntity = this.findByUsername(username);
		UserPrincipal userPrincipal = null;
		if (accountEntity != null) {
			userPrincipal = new UserPrincipal(accountEntity);

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + accountEntity.getRole()));
			for (AccountRole right : roleService.findByAccountId(accountEntity.getId())) {
				grantedAuthorities.add(new SimpleGrantedAuthority(right.getAction()));
			}
			if (accountEntity.getGroupAccount() != null) {
				for (AccountRole right : roleService.findByGroupId(accountEntity.getGroupAccount().getId())) {
					grantedAuthorities.add(new SimpleGrantedAuthority(right.getAction()));
				}
			}
			userPrincipal.setAuthorities(grantedAuthorities);

			WebAuthenticationDetails details = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				Object authDetails = authentication.getDetails();
				if (authDetails instanceof WebAuthenticationDetails) {
					details = (WebAuthenticationDetails) authDetails;
				}
			}
			userPrincipal.setIp(details != null ? details.getRemoteAddress() : "unknown");
			userPrincipal.setCreatedBy(accountEntity.getId());
			userPrincipal.setLastUpdatedBy(accountEntity.getUsername());
			SystemLog systemLog = SystemLog.builder().module(MODULE.SYSTEM.name()).function(ACTION.SYS_LOGIN.name()).object(mainObjectName).mode(LogType.LI.name()).content(accountEntity.getUsername()).ip(userPrincipal.getIp()).account(accountEntity).build();
			systemLog.setCreatedBy(accountEntity.getId());
			systemLogRepo.save(systemLog);
		} else {
            logger.error("User not found with username: {}", username);
		}
		return userPrincipal;
	}

	@Override
	public Optional<Account> findById(Integer accountId) {
		return accountRepo.findById(accountId);
	}

	@Override
	public Account save(Account account) {
		try {
			if (account.getRole() != null && account.getRole().equals(AppConstants.ADMINISTRATOR)) {
				account.setRole("ADMIN");
			} else {
				account.setRole("USER");
			}
			BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
			String password = account.getPassword();
			account.setPassword(bCrypt.encode(password));
			Account accountSaved = accountRepo.save(account);
			systemLogService.writeLogCreate(MODULE.SYSTEM.name(), ACTION.SYS_ACC_C.name(), mainObjectName, "Thêm mới account", account.getUsername());
            logger.info("Insert account success! username={}", account.getUsername());
			return accountSaved;
		} catch (RuntimeException ex) {
			throw new AppException("Insert account fail! username=" + account.getUsername(), ex);
		}
	}

	@Transactional
	@Override
	public Account update(Account account, Integer entityId) {
		try {
			account.setId(entityId);
			if (account.getRole() != null && account.getRole().equals(AppConstants.ADMINISTRATOR)) {
				account.setRole("ADMIN");
			} else {
				account.setRole("USER");
			}
			systemLogService.writeLogUpdate(MODULE.SYSTEM.name(), ACTION.SYS_ACC_U.name(), mainObjectName, "Cập nhật account", account.getUsername());
			logger.info("Update account success! username={}", account.getUsername());
			return accountRepo.save(account);
		} catch (RuntimeException ex) {
			throw new AppException("Update account fail! username=" + account.getUsername(), ex);
		}
	}

	@Transactional
	@Override
	public String delete(Integer accountId) {
		try {
			Optional<Account> account = accountRepo.findById(accountId);
			if (account.isPresent()) {
				accountRepo.delete(account.get());
				systemLogService.writeLogDelete(MODULE.SYSTEM.name(), ACTION.SYS_ACC_D.name(), mainObjectName, "Xóa account", account.get().getUsername());
                logger.info("Delete account success! username={}", account.get().getUsername());
			}
			return MessageUtils.DELETE_SUCCESS;
		} catch (Exception ex) {
			throw new AppException("Delete account fail! id=" + accountId, ex);
		}
	}

	@Override
	public List<Account> findAll() {
		return accountRepo.findAll();
	}

	@Override
	public Account findByUsername(String username) {
		return accountRepo.findByUsername(username);
	}
}