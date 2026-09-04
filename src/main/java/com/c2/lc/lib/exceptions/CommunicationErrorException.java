package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.utils.AppStatus;

public class CommunicationErrorException extends ErrorException {

	private static final long serialVersionUID = 4853190525147827349L;
	
	public CommunicationErrorException(String value, String message) {
		super(AppStatus.APP_CODE_COMMUNICATION_ERROR, value, message);
	}

	public CommunicationErrorException(String value, String message, String locale) {
		super(AppStatus.APP_CODE_COMMUNICATION_ERROR, value, message, locale);
	}
}
