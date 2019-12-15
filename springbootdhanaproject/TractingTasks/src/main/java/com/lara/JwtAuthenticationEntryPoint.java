package com.lara;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
/*
 * 
 * jwt token is Unauthorized
 * 
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lara.exception.ErrorResponse;

 /*
  * 
  * unauthorized users get exception
  */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		  //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		  
		  ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatus(401);
			errorResponse.setMessage("unauthorized user");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue( response.getOutputStream(), errorResponse);
			response.flushBuffer();
	}

}
