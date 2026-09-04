package com.c2.lc.lib.security.interfaces;

import java.security.NoSuchAlgorithmException;

public interface OTPGenerator {

	String generateOTP() throws NoSuchAlgorithmException;

}
