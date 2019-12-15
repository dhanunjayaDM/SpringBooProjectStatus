package com.lara.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class LogoutController 
{
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	public ResponseEntity<Void> logoutUser(HttpSession session) 
	{
		session.removeAttribute("loginuser");
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
