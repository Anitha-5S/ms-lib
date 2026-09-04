package com.c2.lc.lib.exceptions;


import com.c2.lc.lib.utils.AppStatus;

public class UserAuthenticationException extends ErrorException {

	private static final long serialVersionUID = -4628594656015065875L;
	
	public UserAuthenticationException(String value, String message) {
		super(AppStatus.APP_CODE_AUTHENTICATION_ERROR, value, message);
	}

	public UserAuthenticationException(int code, String value, String message) {
		super(code, value, message);
	}


}
