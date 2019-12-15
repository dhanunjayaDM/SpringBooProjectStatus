package com.lara.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.lara.model.Users;

@Service
public interface UserService {

	Users getUserByName(String username);
	public Users  getUserById(Integer user_id);
	List<Users> getAllUsers();
	void saveUser(Users user);
	boolean duplicateChecking(Users user, HttpServletRequest req);
	Users getUserByUsername(String username);
	void deleteUser(Integer user_id, Integer userid);
	void reactivateRecord(Integer user_id, Integer userid);
}
