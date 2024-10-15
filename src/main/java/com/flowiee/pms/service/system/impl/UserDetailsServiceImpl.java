package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.model.UserPrincipal;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.repository.system.SystemLogRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.RoleService;

import com.flowiee.pms.utils.AppConstants;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl extends BaseService implements UserDetailsService, AccountService {
	RoleService mvRoleService;
	AccountRepository mvAccountRepository;
	SystemLogRepository mvSystemLogRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account accountEntity = this.findByUsername(username);
		UserPrincipal userPrincipal = null;
		if (accountEntity != null) {
			userPrincipal = new UserPrincipal(accountEntity);

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + accountEntity.getRole()));
			for (AccountRole right : mvRoleService.findByAccountId(accountEntity.getId())) {
				grantedAuthorities.add(new SimpleGrantedAuthority(right.getAction()));
			}
			if (accountEntity.getGroupAccount() != null) {
				for (AccountRole right : mvRoleService.findByGroupId(accountEntity.getGroupAccount().getId())) {
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

			SystemLog systemLog = SystemLog.builder()
					.module(MODULE.SYSTEM.name())
					.function(ACTION.SYS_LOGIN.name())
					.object(MasterObject.Account.name())
					.mode(LogType.LI.name())
					.content(accountEntity.getUsername() + " login")
					.title("Login")
					.ip(userPrincipal.getIp())
					.account(accountEntity)
					.build();
			systemLog.setCreatedBy(accountEntity.getId());

			mvSystemLogRepository.save(systemLog);
		} else {
            logger.error("User not found with username: {}", username);
		}
		return userPrincipal;
	}

	@Override
	public Optional<Account> findById(Integer accountId) {
		return mvAccountRepository.findById(accountId);
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
			Account accountSaved = mvAccountRepository.save(account);
			systemLogService.writeLogCreate(MODULE.SYSTEM, ACTION.SYS_ACC_C, MasterObject.Account, "Thêm mới account", account.getUsername());
            logger.info("Insert account success! username={}", account.getUsername());
			return accountSaved;
		} catch (RuntimeException ex) {
			throw new AppException("Insert account fail! username=" + account.getUsername(), ex);
		}
	}

	@Transactional
	@Override
	public Account update(Account account, Integer entityId) {
		Optional<Account> accountOpt = this.findById(entityId);
		if (accountOpt.isEmpty()) {
			throw new ResourceNotFoundException("Account not found!");
		}
		Account accountBefore = ObjectUtils.clone(accountOpt.get());
		try {
			accountOpt.get().setPhoneNumber(account.getPhoneNumber());
			accountOpt.get().setEmail(account.getEmail());
			accountOpt.get().setAddress(account.getAddress());
			accountOpt.get().setGroupAccount(account.getGroupAccount());
			accountOpt.get().setBranch(account.getBranch());

			if (account.getRole() != null && account.getRole().equals(AppConstants.ADMINISTRATOR)) {
				accountOpt.get().setRole("ADMIN");
			} else {
				accountOpt.get().setRole("USER");
			}
			Account accountUpdated = mvAccountRepository.save(accountOpt.get());

			ChangeLog changeLog = new ChangeLog(accountBefore, accountUpdated);
			systemLogService.writeLogUpdate(MODULE.SYSTEM, ACTION.SYS_ACC_U, MasterObject.Account, "Cập nhật tài khoản " + accountUpdated.getUsername(), changeLog);
			logger.info("Update account success! username={}", accountUpdated.getUsername());

			return accountUpdated;
		} catch (RuntimeException ex) {
			throw new AppException("Update account fail! username=" + account.getUsername(), ex);
		}
	}

	@Transactional
	@Override
	public String delete(Integer accountId) {
		try {
			Optional<Account> account = mvAccountRepository.findById(accountId);
			if (account.isPresent()) {
				mvAccountRepository.delete(account.get());
				systemLogService.writeLogDelete(MODULE.SYSTEM, ACTION.SYS_ACC_D, MasterObject.Account, "Xóa account", account.get().getUsername());
                logger.info("Delete account success! username={}", account.get().getUsername());
			}
			return MessageCode.DELETE_SUCCESS.getDescription();
		} catch (Exception ex) {
			throw new AppException("Delete account fail! id=" + accountId, ex);
		}
	}

	@Override
	public List<Account> findAll() {
		return mvAccountRepository.findAll();
	}

	@Override
	public Account findByUsername(String username) {
		return mvAccountRepository.findByUsername(username);
	}
}