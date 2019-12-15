
package com.lara.controller;



import javax.servlet.ServletException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.lara.TokenProvider;

//import com.lara.TokenProvider;
import com.lara.model.JwtResponse;
import com.lara.model.LoginUser;





@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController 
{ 
	
	
     @Autowired
     private AuthenticationManager authenticationManager;

	 @Autowired
	 private TokenProvider jwtTokenUtil;
	
	
	@RequestMapping(value = "/loginjwt", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> login(@RequestBody LoginUser loginuser) throws ServletException  {
		
		 final Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                		loginuser.getUsername(),
	                		loginuser.getPassword()
	                )
	        );
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        final String token = jwtTokenUtil.generateToken(authentication);


		return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);
	}
	
}
