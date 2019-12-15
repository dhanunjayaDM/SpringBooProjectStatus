package com.lara;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lara.exception.ErrorResponse;
/*
 * authorized user with no access rids
 * 
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setStatus(403);
		errorResponse.setMessage("Access denied");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue( response.getOutputStream(), errorResponse);
		response.flushBuffer();
	}

}
