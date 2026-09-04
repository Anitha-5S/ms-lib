package com.c2.lc.lib.exceptions;

import com.c2.lc.lib.utils.AppStatus;
import com.c2.lc.lib.utils.Constants;

public class SessionExpiredException extends AppErrorException {

    public SessionExpiredException(String message) {
        super(AppStatus.APP_CODE_SESSION_EXPIRED, Constants.EMPTY_STRING, message);
    }

    public SessionExpiredException(String message, String locale) {
        super(AppStatus.APP_CODE_SESSION_EXPIRED, message, locale);
    }
}
