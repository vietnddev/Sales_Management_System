package com.flowiee.app.authentication;

import com.flowiee.app.model.admin.Account;
import com.flowiee.app.model.admin.Log;
import com.flowiee.app.services.AccountService;
import com.flowiee.app.services.LogService;
import com.flowiee.app.utils.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountDetailService implements UserDetailsService {
	@Autowired
	AccountService accountService;

	@Autowired
	LogService logService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountService.getAccountByUsername(username);
		UserDetails userDetails = null; // Đây là class hỗ trợ sẵn của Spring Security

		if (account != null) {			
			// Thiết lập role
			List<GrantedAuthority> grantlist = new ArrayList<GrantedAuthority>();
			GrantedAuthority authority = new SimpleGrantedAuthority("USER");
			grantlist.add(authority);

			userDetails = new org.springframework.security.core.userdetails.User(account.getUsername(),
					account.getPassword(), grantlist);

			String users = account.getName() + " (" + account.getUsername() + ")";
			String type = "Đăng nhập hệ thống";
			String content = "Đăng nhập hệ thống";
			String url = "/login";
			String created = DatetimeUtil.now("yyyy-MM-dd HH:mm:ss");
			String ip = "123";
			logService.insertLog(new Log(users, type, content, url, created, ip));

			System.out.println("Login thành công");
		} else {
			System.out.println("Login thất bại");
		}
		return userDetails;
	}
}
