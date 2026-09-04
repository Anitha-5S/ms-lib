package com.c2.lc.lib.security.interfaces;


import com.c2.lc.lib.exceptions.AppErrorException;

public interface SecurityHashingService {

    boolean compare(String originalPassword, String storedPassword) throws AppErrorException;

	String generateHash(String password) throws AppErrorException;
}
