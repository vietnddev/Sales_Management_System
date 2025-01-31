package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.*;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.AccountRole;
import com.flowiee.pms.utils.*;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.config.UserPrincipal;
import com.flowiee.pms.repository.system.AccountRepository;
import com.flowiee.pms.repository.system.SystemLogRepository;
import com.flowiee.pms.base.service.BaseService;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.service.system.RoleService;

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
			if (accountEntity.isLocked()) {
				throw new AccountLockedException();
			}
			if (accountEntity.isPasswordExpired()) {
				throw new AppException("Password has expired for operator " + accountEntity.getUsername());
			}

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
	public Account findById(Long accountId, boolean pThrowException) {
		Optional<Account> entityOptional = mvAccountRepository.findById(accountId);
		if (entityOptional.isEmpty() && pThrowException) {
			throw new EntityNotFoundException(new Object[] {"account"}, null, null);
		}
		return entityOptional.orElse(null);
	}

	@Override
	public Account save(Account account) {
		String name = account.getFullName();
		String username = account.getUsername();
		String email = account.getEmail();
		String lvRole = account.getRole();
		String lvPassword = account.getPassword();

		if (CoreUtils.isAnySpecialCharacter(name))
			throw new BadRequestException(String.format("Account name can't allow include special characters!", name));
		if (mvAccountRepository.findByUsername(username) != null)
			throw new DataExistsException(String.format("Username %s existed!", username));
		if (!CoreUtils.validateEmail(email))
			throw new DataExistsException(String.format("Email %s invalid!", email));
		if (mvAccountRepository.findByEmail(email) != null)
			throw new DataExistsException(String.format("Email %s existed!", email));

		account.setRole(AppConstants.ADMINISTRATOR.equals(lvRole) ? "ADMIN" : "USER");
		account.setPassword(PasswordUtils.encodePassword(lvPassword));
		Account accountSaved = mvAccountRepository.save(account);

		systemLogService.writeLogCreate(MODULE.SYSTEM, ACTION.SYS_ACC_C, MasterObject.Account, "Thêm mới account", username);
		logger.info("Insert account success! username={}", username);

		return accountSaved;
	}

	@Transactional
	@Override
	public Account update(Account account, Long entityId) {
		Account accountOpt = this.findById(entityId, true);

		Account accountBefore = ObjectUtils.clone(accountOpt);

		accountOpt.setPhoneNumber(account.getPhoneNumber());
		accountOpt.setEmail(account.getEmail());
		accountOpt.setAddress(account.getAddress());
		accountOpt.setGroupAccount(account.getGroupAccount());
		accountOpt.setBranch(account.getBranch());
		accountOpt.setRole(AppConstants.ADMINISTRATOR.equals(account.getRole()) ? "ADMIN" : "USER");

		Account accountUpdated = mvAccountRepository.save(accountOpt);

		ChangeLog changeLog = new ChangeLog(accountBefore, accountUpdated);
		systemLogService.writeLogUpdate(MODULE.SYSTEM, ACTION.SYS_ACC_U, MasterObject.Account, "Cập nhật tài khoản " + accountUpdated.getUsername(), changeLog);
		logger.info("Update account success! username={}", accountUpdated.getUsername());

		return accountUpdated;
	}

	@Transactional
	@Override
	public String delete(Long accountId) {
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