package com.c2.lc.lib.security;


import com.c2.lc.lib.exceptions.AppErrorException;
import com.c2.lc.lib.properties.Messages;
import com.c2.lc.lib.security.interfaces.SecurityHashingService;
import com.c2.lc.lib.utils.Constants;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class SecurityHashingServiceImpl implements SecurityHashingService {

	private static final String HASHING_KEY = "PBKDF2WithHmacSHA1";
	private static final String NO_SUCH_ALGORITHM = "NoSuchAlgorithmException";


	@Override
	public boolean compare(String originalPassword, String storedPassword) throws AppErrorException {
		String[] parts = storedPassword.split(":");
		byte[] salt = fromHex(parts[0]);
		int iterations = Integer.parseInt(parts[1]);
		byte[] hash = fromHex(parts[2]);

		PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf;
		try {
			skf = SecretKeyFactory.getInstance(HASHING_KEY);
		} catch (NoSuchAlgorithmException e) {
			throw new AppErrorException(NO_SUCH_ALGORITHM, Messages.APPLICATION_ERROR);
		}

		byte[] testHash = null;
		try {
			testHash = skf.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			throw new AppErrorException("InvalidKeySpecException", Messages.APPLICATION_ERROR);
		}

		int diff = hash.length ^ testHash.length;
		for (int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}

		return diff == 0;
	}

	@Override
	public String generateHash(String password) throws AppErrorException {
		int iterations = 999;
		char[] chars = password.toCharArray();
		byte[] salt = null;
		try {
			salt = getSalt().getBytes();
		} catch (NoSuchAlgorithmException e) {
			throw new AppErrorException(NO_SUCH_ALGORITHM, Messages.APPLICATION_ERROR);
		}

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = null;
		try {
			skf = SecretKeyFactory.getInstance(HASHING_KEY);
		} catch (NoSuchAlgorithmException e) {
			throw new AppErrorException(NO_SUCH_ALGORITHM, Messages.APPLICATION_ERROR);
		}
		byte[] hash = null;
		try {
			hash = skf.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e) {
			throw new AppErrorException("InvalidKeySpecException", Messages.APPLICATION_ERROR);
		}

		password = toHex(salt) + ":" + iterations + ":" + toHex(hash);

		if (password.length() > Constants.HASH_TOKEN_LENGTH) {
			password = password.substring(0, Constants.HASH_TOKEN_LENGTH);
		}

		return password;

	}

	private static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	private static byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
