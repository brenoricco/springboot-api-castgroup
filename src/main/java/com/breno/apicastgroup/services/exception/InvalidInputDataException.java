package com.breno.apicastgroup.services.exception;

public class InvalidInputDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidInputDataException(String msg) {
		super(msg);
	}

}
