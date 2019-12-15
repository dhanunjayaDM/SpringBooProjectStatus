package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.lara.model.Role;

@Service
public interface RoleService {

	void saveRole(Role role);
	
	public boolean duplicateChecking(Role role,HttpServletRequest req) ;

	List<Role> getAllRole();

	Role getRoleById(Integer role_id);

}
