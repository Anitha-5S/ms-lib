package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.utils.Constants;

public class RecordNotFoundException extends WarningException {

	private static final long serialVersionUID = -2685024653322348532L;

	public RecordNotFoundException(String message) {
		super(message);
	}

	public RecordNotFoundException(String value, String message) {
		super(value, message);
	}

	public RecordNotFoundException(String value, String message, String locale) {
		super(value, message, locale);
	}

	public RecordNotFoundException(long value, String message) {
		super(Constants.EMPTY_STRING + value, message);
	}

	public RecordNotFoundException(long value, String message, String locale) {
		super(Constants.EMPTY_STRING + value, message, locale);
	}
}
