package com.web.exceptions;

public class SelfResolverException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public SelfResolverException(String message) {
		super(message);
	}

}
