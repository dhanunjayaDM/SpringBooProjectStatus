package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lara.dao.UserDao;
import com.lara.model.Users;
@Service
@Transactional
public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserDao userDao;

	@Override
	public Users getUserByName(String username) {

		return userDao.getUserByName(username);
	}
	
	public Users  getUserById(Integer id){
		return userDao.getUserById(id);
	}

	@Override
	public List<Users> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public void saveUser(Users user) {
		System.out.println("user service");
		userDao.saveUser(user);
	}

	@Override
	public boolean duplicateChecking(Users user, HttpServletRequest req) {
		
		return userDao.duplicateChecking(user,req);
	}

	@Override
	public Users getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public void deleteUser(Integer user_id, Integer userid) {
		userDao.deleteUser(user_id,userid);
	}

	@Override
	public void reactivateRecord(Integer user_id, Integer userid) {
		userDao.reactivateRecord(user_id,userid);
		
	}

}
