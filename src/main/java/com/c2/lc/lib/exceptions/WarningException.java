package com.c2.lc.lib.exceptions;

public class WarningException extends BaseException {

	private static final long serialVersionUID = -4289323001851030542L;

	public WarningException(String message) {
		super(message);
	}

	public WarningException(String value, String message) {
		super(value, message);
	}

	public WarningException(String value, String message, String locale) {
		super(value, message, locale);
	}

}
