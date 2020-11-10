package com.reimbursement.controller;

public class NegativeAmountException extends Exception {
	
	
	private static final long serialVersionUID = 1L;
	
	public NegativeAmountException(String message) {
		super(message);
	}

}
