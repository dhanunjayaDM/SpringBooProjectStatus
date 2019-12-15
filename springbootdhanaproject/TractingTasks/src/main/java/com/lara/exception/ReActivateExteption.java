package com.lara.exception;



//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReActivateExteption extends RuntimeException  {
	private static final long serialVersionUID = 1L;
	
	public ReActivateExteption(String string) {
		super(string);
	}

	public ReActivateExteption(Exception e) {
		super(e);
	}
	public ReActivateExteption() {
		
	}

	

}
