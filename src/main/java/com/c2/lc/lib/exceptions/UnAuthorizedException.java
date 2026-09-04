package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.utils.AppStatus;
import com.c2.lc.lib.utils.Constants;

public class UnAuthorizedException extends ErrorException {

	private static final long serialVersionUID = 3644711150115820381L;
	
	public UnAuthorizedException(String message) {
		super(AppStatus.APP_CODE_UNAUTHORIZED_ERROR, Constants.EMPTY_STRING, message);
	}

	public UnAuthorizedException(String message, String locale) {
		super(AppStatus.APP_CODE_UNAUTHORIZED_ERROR, Constants.EMPTY_STRING, message, locale);
	}

}
