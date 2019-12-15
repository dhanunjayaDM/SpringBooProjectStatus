package com.lara.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lara.exception.DuplicateException;
import com.lara.exception.ReActivateExteption;
import com.lara.model.Role;
import com.lara.service.RoleService;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	
	@RequestMapping(value="/saveRole",method=RequestMethod.POST)
	public ResponseEntity<String> saveRole(@RequestBody Role role, HttpSession session,HttpServletRequest req) 
	{
		
		System.out.println("role controller");
		try {
			if(roleService.duplicateChecking(role, req))
			{
				throw new DuplicateException();
			}
			else {
				String username=SecurityContextHolder.getContext().getAuthentication().getName();
				System.out.println(username);
				Integer userid=com.lara.TractingTasksApplication.map.get(username);
			    role.setCreatedBy(userid.toString());
			    System.out.println("username");
			    roleService.saveRole(role);
			}
         }catch(ReActivateExteption  e) {
			
			if(e instanceof ReActivateExteption) {
				System.out.println(e.getMessage());
				throw new ReActivateExteption(e.getMessage());
			}
		}
		return  new ResponseEntity<String>( "record is created successfully",HttpStatus.CREATED);
			
		
	}
	@RequestMapping(value="/getAllRole",method=RequestMethod.GET)
	public ResponseEntity<List<Role>> getAllRole( ) 
	{	
		List<Role> roles=roleService.getAllRole();
		return new ResponseEntity<List<Role>>(roles,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getRole/{role_id}",method=RequestMethod.GET)
	public ResponseEntity<Role> getRole(@PathVariable Integer role_id) 
	{
		Role role=roleService.getRoleById(role_id);
		return new ResponseEntity<Role>(role,HttpStatus.OK);
	}

}
