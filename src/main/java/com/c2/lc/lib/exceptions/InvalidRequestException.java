package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.utils.AppStatus;
import com.c2.lc.lib.utils.Constants;

public class InvalidRequestException extends ErrorException {

	public InvalidRequestException() {
		super(AppStatus.APP_CODE_INVALID_REQUEST, Constants.EMPTY_STRING, Constants.EMPTY_STRING);
	}

	public InvalidRequestException(String value, String message) {
		super(AppStatus.APP_CODE_INVALID_REQUEST, value, message);
	}

	public InvalidRequestException(String value, String message, String locale) {
		super(AppStatus.APP_CODE_INVALID_REQUEST, value, message, locale);
	}
}
