package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.utils.Constants;

public class DuplicateRecordException extends WarningException {

	private static final long serialVersionUID = 4268735337842867023L;

	public DuplicateRecordException(String message) {
		super(message);
	}
	public DuplicateRecordException(String value, String message) {
		super(value, message);
	}

	public DuplicateRecordException(String value, String message, String locale) {
		super(value, message, locale);
	}

	public DuplicateRecordException(long value, String message) {
		super(Constants.EMPTY_STRING + value, message);
	}

	public DuplicateRecordException(long value, String message, String locale) {
		super(Constants.EMPTY_STRING + value, message, locale);
	}

}
