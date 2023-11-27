package com.flowiee.app.security.authen;

import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.SystemLogService;
import com.flowiee.app.model.role.SystemAction;
import com.flowiee.app.model.role.SystemModule;

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
	@Autowired
	AccountService accountService;

	@Autowired
	SystemLogService systemLogService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account accountEntity = accountService.findByUsername(username);
		UserDetails userDetails = null; // Đây là class hỗ trợ sẵn của Spring Security

		if (accountEntity != null) {
			// Thiết lập role
			List<GrantedAuthority> grantlist = new ArrayList<GrantedAuthority>();
			GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + accountEntity.getRole());
			grantlist.add(authority);

			userDetails = new org.springframework.security.core.userdetails.User(accountEntity.getUsername() + "_" + accountEntity.getId(), accountEntity.getPassword(), grantlist);
			WebAuthenticationDetails details = null;
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				Object authDetails = authentication.getDetails();
				if (authDetails instanceof WebAuthenticationDetails) {
					details = (WebAuthenticationDetails) authDetails;
				}
			}
			SystemLog systemLog = new SystemLog(SystemModule.HE_THONG.name(), SystemAction.LOGIN.name(), "LOGIN", null, accountEntity.getId(), details != null ? details.getRemoteAddress() : "unknown");
			systemLogService.writeLog(systemLog);
		} else {
			System.out.println("Login thất bại");
		}
		return userDetails;
	}
}