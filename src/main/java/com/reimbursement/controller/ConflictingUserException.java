package com.reimbursement.controller;

public class ConflictingUserException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ConflictingUserException(String message) {
		super(message);
	}

}
