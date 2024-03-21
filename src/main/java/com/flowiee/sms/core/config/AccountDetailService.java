package com.flowiee.sms.core.config;

import com.flowiee.sms.entity.Account;
import com.flowiee.sms.entity.AccountRole;
import com.flowiee.sms.entity.SystemLog;
import com.flowiee.sms.model.ACTION;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.model.UserPrincipal;
import com.flowiee.sms.service.AccountService;
import com.flowiee.sms.service.RoleService;
import com.flowiee.sms.service.SystemLogService;

import com.flowiee.sms.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDetailService implements UserDetailsService {
	@Autowired private AccountService accountService;
	@Autowired private SystemLogService systemLogService;
	@Autowired private RoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account accountEntity = accountService.findByUsername(username);
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
			systemLogService.writeLog(systemLog);
		} else {
			System.out.println("Login thất bại");
		}
		return userPrincipal;
	}
}