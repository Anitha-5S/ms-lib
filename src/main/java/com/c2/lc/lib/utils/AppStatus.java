package com.c2.lc.lib.utils;

public class AppStatus {

	private AppStatus() { super(); }

	public static final int APP_CODE_SUCCESS= 0;
	public static final int APP_CODE_MISSING_FIELDS = 1;
	public static final int APP_CODE_DUPLICATE_RECORD = 2;
	public static final int APP_CODE_INVALID_DATA_FORMAT = 3;
	public static final int APP_CODE_DATA_INPUT_ERROR = 4;
	public static final int APP_CODE_RECORD_NOT_FOUND = 5;
	public static final int APP_CODE_LOGIN_FAILED = 6;
	public static final int APP_CODE_INACTIVE = 7;
	public static final int APP_CODE_PENDING = 8;
	public static final int APP_CODE_LOCKED = 9;
	public static final int APP_CODE_LOGOUT = 10;
	public static final int APP_CODE_INVALID_REQUEST = 11;
	public static final int APP_CODE_REQUEST_ALREADY_PROCESS = 12;
	public static final int APP_CODE_COMMUNICATION_ERROR = 13;
	public static final int APP_CODE_DATA_CALCULATION_ERROR = 14;
	public static final int APP_CODE_UNAUTHORIZED_ERROR = 15;
	public static final int APP_CODE_AUTHENTICATION_ERROR = 16;
	public static final int APP_CODE_SESSION_EXPIRED = 17;

	public static final int APP_CODE_SQL_EXCEPTION = 18;
	public static final int APP_CODE_APPLICATION_ERROR = 100;

}
