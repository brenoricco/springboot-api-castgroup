package com.breno.apicastgroup.services.exception;

public class SendMailException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public SendMailException(String msg) {
		super(msg);
	}

}
