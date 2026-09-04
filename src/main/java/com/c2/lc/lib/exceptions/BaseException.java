package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.properties.AppMessages;

public class BaseException extends Exception {

	private static final long serialVersionUID = -6383832410891395697L;

	public BaseException(String message) {
		super(AppMessages.getPropertyValue(message));
	}

	public BaseException(String value, String message) {
		super(value != null && !value.isBlank() ? "'"+value+"' : " + AppMessages.getPropertyValue(message) : AppMessages.getPropertyValue(message));
	}

	public BaseException(String value, String message, String locale) {
		super(value != null && !value.isBlank() ? "'"+value+"' : " + AppMessages.getPropertyValue(message, locale) : AppMessages.getPropertyValue(message, locale));
	}
}
