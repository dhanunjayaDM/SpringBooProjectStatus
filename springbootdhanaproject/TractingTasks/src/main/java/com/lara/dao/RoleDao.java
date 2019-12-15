package com.lara.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.lara.model.Role;

@Repository
public interface RoleDao {
	
	void saveRole(Role role);
	
	List<Role> getAllRole();
	Role getRoleById(Integer role_id);

//	Role updateRole(Integer role_id, Role role);

//	void deleteProject(Integer project_id, Integer userid);

//	void reActivateRecord(Integer project_id, Integer userid);

	boolean duplicateChecking(Role role, HttpServletRequest req);

}
