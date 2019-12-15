package com.lara;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lara.model.Users;

public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private Users user;
	
	public UserPrincipal(Users user){
        this.user = user;
    }


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		   List<GrantedAuthority> authorities = new ArrayList<>();

	     
	        this.user.getPermissionList().forEach(p -> {
	            GrantedAuthority authority = new SimpleGrantedAuthority(p);
	            authorities.add(authority);
	        });

	     
	        this.user.getRoles().forEach(r -> {
	            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+r.getUserRole());
	            authorities.add(authority);
	        });

	        return authorities;
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.user.getIsActive();
	}

}
