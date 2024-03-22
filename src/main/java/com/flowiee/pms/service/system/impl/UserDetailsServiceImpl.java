package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.UserPrincipal;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.RoleService;
import com.flowiee.pms.service.system.SystemLogService;

import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, AccountService {
	@Autowired private AccountRepository accountRepo;
	@Autowired private SystemLogService systemLogService;
	@Autowired private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account accountEntity = this.findByUsername(username);
		UserPrincipal userPrincipal = null;
		if (accountEntity != null) {
			userPrincipal = new UserPrincipal(accountEntity);

			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + accountEntity.getRole());
			grantedAuthorities.add(authority);
			for (AccountRole rights : roleService.findByAccountId(accountEntity.getId())) {
				GrantedAuthority rightsAction = new SimpleGrantedAuthority(rights.getAction());
				grantedAuthorities.add(rightsAction);
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

			SystemLog systemLog = new SystemLog(MODULE.SYSTEM.name(), ACTION.SYS_LOGIN.name(), "LOGIN", null, accountEntity.getId(), details != null ? details.getRemoteAddress() : "unknown");
			systemLog.setCreatedBy(accountEntity.getId());
			systemLog.setLastUpdatedBy(accountEntity.getUsername());
			systemLogService.writeLog(systemLog);
		} else {
			System.out.println("Login thất bại");
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
		} catch (RuntimeException ex) {
			throw new AppException("Insert account fail! username=" + account.getUsername(), ex);
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
				SystemLog systemLog = new SystemLog(MODULE.SYSTEM.name(), ACTION.SYS_ACCOUNT_DELETE.name(), "Xóa account " + account.get().getUsername(), null, CommonUtils.getUserPrincipal().getId(), CommonUtils.getUserPrincipal().getIp());
				systemLogService.writeLog(systemLog);
				logger.info("Delete account success! username=" + account.get().getUsername());
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