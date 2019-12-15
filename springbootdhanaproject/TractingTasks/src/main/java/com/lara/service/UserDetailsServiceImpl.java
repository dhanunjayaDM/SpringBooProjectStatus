package com.lara.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lara.UserPrincipal;

import com.lara.model.Users;
@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userService.getUserByName(username);
		UserPrincipal userPrincipal=new UserPrincipal(user);
		return userPrincipal;
		
	}

}
