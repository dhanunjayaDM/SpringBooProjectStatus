package com.lara.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lara.exception.DuplicateException;
import com.lara.exception.ReActivateExteption;
import com.lara.model.Role;
import com.lara.model.Users;
import com.lara.service.UserService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/user")
public class UserController
{
	@Autowired
	private UserService userService;
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/saveUser",method=RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody Users user, HttpSession session,HttpServletRequest req) 
	{
	    //String roles=req.getParameter("roles");
	    //System.out.println(roles);
		System.out.println("user object");
		System.out.println(user);
		System.out.println((user.getRoles()));
		for(Role role:user.getRoles()) {
			System.out.println(role.getId());
		}
		
		//System.out.println(role);
		System.out.println(user.getPassword());
		System.out.println(user.getRoles());
		try {
			if(userService.duplicateChecking(user, req))
			{
				throw new DuplicateException();
			}
			else {
				String username=SecurityContextHolder.getContext().getAuthentication().getName();
				System.out.println(username);
				Integer userid=com.lara.TractingTasksApplication.map.get(username);
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(user.getPassword());
				System.out.println(hashedPassword);
				user.setCreatedBy(userid.toString());
				user.setPassword(hashedPassword);
				userService.saveUser(user);
				new ResponseEntity<String>("record is created successfully", HttpStatus.CREATED);	
			}
		}catch(ReActivateExteption  e) {
			
			if(e instanceof ReActivateExteption) {
				System.out.println(e.getMessage());
				throw new ReActivateExteption(e.getMessage());
			}
		}
		return  new ResponseEntity<String>( "record is creatd successfully",HttpStatus.CREATED);	
	
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(value="/getAllUsers",method=RequestMethod.GET)
	public ResponseEntity<List<Users>> getAllUsers( ) 
	{	
		System.out.println("user list con");
		List<Users> users=userService.getAllUsers();
		return new ResponseEntity<List<Users>>(users,HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/username/{username}",method=RequestMethod.GET)
	public ResponseEntity<Users> getUser(@PathVariable("username") String username) 
	{	
		Users users=userService.getUserByUsername(username);
		return new ResponseEntity<Users>(users,HttpStatus.OK);
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/deleteUser/{user_id}",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable Integer user_id ) 
	{
		System.out.println(user_id);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		userService.deleteUser(user_id,userid);
		return new ResponseEntity<String>( "record is deleted successfully",HttpStatus.OK);	
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/reactivateUser/{user_id}",method=RequestMethod.GET)
	public ResponseEntity<Void> reactivateTaskType(@PathVariable Integer user_id) 
	{
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
	    System.out.println(username);
		Integer userid=com.lara.TractingTasksApplication.map.get(username);
		System.out.println("controller");
		userService.reactivateRecord(user_id, userid);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
