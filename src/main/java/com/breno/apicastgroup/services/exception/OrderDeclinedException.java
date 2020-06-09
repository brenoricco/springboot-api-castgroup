package com.breno.apicastgroup.services.exception;

public class OrderDeclinedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public OrderDeclinedException(String msg) {
		super(msg);
	}

}
