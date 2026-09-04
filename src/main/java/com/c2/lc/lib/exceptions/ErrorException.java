package com.c2.lc.lib.exceptions;

public class ErrorException extends BaseException {

	private static final long serialVersionUID = 7662782479345726671L;
	private int errorCode ;

	public ErrorException(int code, String value, String message) {
		super(value, message);
		this.errorCode = code;
	}

	public ErrorException(int code, String value, String message, String locale) {
		super(value, message, locale);
		this.errorCode = code;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
