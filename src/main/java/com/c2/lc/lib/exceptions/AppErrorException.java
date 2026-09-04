package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.utils.AppStatus;
import com.c2.lc.lib.utils.Constants;

public class AppErrorException extends ErrorException {

	private static final long serialVersionUID = 4853190525147827349L;

	public AppErrorException(String value, String message) {
		super(AppStatus.APP_CODE_APPLICATION_ERROR, value, message);
	}

	public AppErrorException(String value, String message, String locale) {
		super(AppStatus.APP_CODE_APPLICATION_ERROR, value, message, locale);
	}

	public AppErrorException(long value, String message) {
		super((int) value, Constants.EMPTY_STRING, message);
	}

	public AppErrorException(long value, String message, String locale) {
		super((int) value, Constants.EMPTY_STRING, message, locale);
	}
}
