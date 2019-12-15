package com.simpedb.core.exceptions;

import com.simpedb.core.constant.ErrorCodes;

public class SimpleDBException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SimpleDBException(ErrorCodes message, Throwable cause, String ... args) {
		super(ErrorCodes.getErrorString(message, args), cause);
		
	}

	public SimpleDBException(ErrorCodes message, String ... args) {
		super(ErrorCodes.getErrorString(message, args));
	}
	

}
