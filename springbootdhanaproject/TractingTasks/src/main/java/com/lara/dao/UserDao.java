package com.lara.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;

import com.lara.model.Users;

@Repository
public interface UserDao {

	Users getUserByName(String username);

	Users getUserById(Integer id);

	List<Users> getAllUsers();

	void saveUser(Users user);

	boolean duplicateChecking(Users user, HttpServletRequest req);

	Users getUserByUsername(String username);

	void deleteUser(Integer user_id, Integer userid);

	void reactivateRecord(Integer user_id, Integer userid);

}
