package com.c2.lc.lib.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

public class SecurityHelper {
	private static final String IV = "CSQUARE";
	private static final String ENCRYPTION_KEY = "0123456789abcdef";
	private static final String FIRST_DELIMITER = "*";
	private static final String SECOND_DELIMITER = "<";
	private static final String UTF_8 = "UTF-8";

	public String getEncryptedString(String url, String password) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		String encryptedPassword = null;
		String plainText = LocalDateTime.now().toString()
							+ FIRST_DELIMITER + password + SECOND_DELIMITER + url;
		byte[] bytearray = encrypt(plainText, ENCRYPTION_KEY);
		encryptedPassword = new String(Base64.encodeBase64(bytearray));
		return encryptedPassword;
	}
	
	public String getDecryptedString(String encryptedText) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		String password = null;

		byte [] bytearray = Base64.decodeBase64(encryptedText);
		String plainText = decrypt(bytearray, ENCRYPTION_KEY);
		int firstIndex = plainText.indexOf(FIRST_DELIMITER);
		int lastIndex = plainText.lastIndexOf(SECOND_DELIMITER);
		password = plainText.substring(firstIndex+1, lastIndex);
	
		return password;
	}

	private byte[] encrypt(String plainText, String encryptionKey) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(UTF_8),"AES");
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(UTF_8)));
		return cipher.doFinal(plainText.getBytes(UTF_8));
	}

	private String decrypt(byte[] cipherText, String encryptionKey) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(UTF_8),"AES");
		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(UTF_8)));
		return new String(cipher.doFinal(cipherText), UTF_8);
	}

}
