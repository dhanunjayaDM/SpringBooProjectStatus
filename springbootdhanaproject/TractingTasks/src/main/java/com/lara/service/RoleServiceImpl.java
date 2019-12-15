package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.dao.RoleDao;
import com.lara.model.Role;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;

	@Override
	public void saveRole(Role role) {
		// TODO Auto-generated method stub
		roleDao.saveRole(role);
	}

	@Override
	public boolean duplicateChecking(Role role, HttpServletRequest req) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		return roleDao.getAllRole();
	}

	@Override
	public Role getRoleById(Integer role_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
