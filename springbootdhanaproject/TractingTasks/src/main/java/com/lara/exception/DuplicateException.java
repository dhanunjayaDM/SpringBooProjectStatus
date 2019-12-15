package com.lara.exception;

public class DuplicateException extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DuplicateException() {
		
	}
   public DuplicateException(Exception e) {
		super(e);
	}

}
