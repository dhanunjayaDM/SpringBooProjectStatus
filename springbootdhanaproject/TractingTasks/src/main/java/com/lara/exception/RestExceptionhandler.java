package com.lara.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class RestExceptionhandler
{

	@ExceptionHandler(value = { ReActivateExteption.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse duplicateRequest(ReActivateExteption ex, HttpServletRequest req)
	{
		System.out.println(ex.getLocalizedMessage().toString());
		req.getSession().getAttribute("activateId") ;
	    return new ErrorResponse(400, ""+ex.getMessage().toString());
	}
	
	@ExceptionHandler(value = { IOException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse badRequest(Exception ex)
	{
	    return new ErrorResponse(400, "Bad Request");
	}
	    
    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse unKnownException(Exception ex)
    {
        return new ErrorResponse(404, "Page Not Found");
    }
    
    @ExceptionHandler(value = { DuplicateException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse duplicateRequest(Exception ex) {
    	
      return new ErrorResponse(409 , "Deplicate record is exist");
    }
    
    @ExceptionHandler(value = { RuntimeException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse internalRequest(Exception ex)
	{
	    return new ErrorResponse(500, "internal server error");
	}
    
    @ExceptionHandler(value = { MyFileNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse filenotfindRequest(Exception ex) {
    
      return new ErrorResponse(404 , "file not find");
    }
    
    
//    @ExceptionHandler(value = { NotAuthorizedException.class })
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ErrorResponse unauthrized(Exception ex) {
//    
//      return new ErrorResponse(401 , "unauthorized");
//    }
    
    
   
   

   
}
