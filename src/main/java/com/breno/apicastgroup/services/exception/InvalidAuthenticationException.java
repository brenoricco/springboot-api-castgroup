package com.breno.apicastgroup.services.exception;

public class InvalidAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidAuthenticationException(String msg) {
		super(msg);
	}

}
