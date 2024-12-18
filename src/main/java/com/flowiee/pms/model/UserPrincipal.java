package com.flowiee.pms.model;

import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPrincipal extends Account implements UserDetails {
	@Serial
	static final long serialVersionUID = 1L;

    Long id;
    String username;
    String password;
    Long branchId;
    String ip;
    boolean isAccountNonExpired;
    boolean isAccountNonLocked;
    boolean isCredentialsNonExpired;
    boolean isEnabled;
    Set<GrantedAuthority> grantedAuthorities;

    public UserPrincipal(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        if (account.getBranch() != null)
            this.branchId = account.getBranch().getId();
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.setEmail(account.getEmail());
    }

    public UserPrincipal(Long id, String username, String ip) {
        this.id = id;
        this.username = username;
        this.ip = ip;
    }

    public void setAuthorities(Set<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public Account toEntity() {
        Account entity = new Account();
        entity.setId(this.id);
        entity.setUsername(this.username);
        return entity;
    }

    public static UserPrincipal anonymousUser() {
        return new UserPrincipal(0l, "anonymous", "unknown");
    }

    public boolean isAdmin() {
        return AppConstants.ADMINISTRATOR.equals(this.username);
    }
}