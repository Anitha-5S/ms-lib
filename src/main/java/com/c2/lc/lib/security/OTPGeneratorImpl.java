package com.c2.lc.lib.security;


import com.c2.lc.lib.security.interfaces.OTPGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class OTPGeneratorImpl implements OTPGenerator {

	@Override
	public String generateOTP() throws NoSuchAlgorithmException {
		Random randomSeries = new Random();
		return this.generatePassword(
				this.generateHash(this.generateHash(Integer
						.toString(Math.abs(randomSeries.nextInt(Integer.MAX_VALUE))))));
	}

	private String generateHash(String input) throws NoSuchAlgorithmException {
		MessageDigest objMesssageDigest = MessageDigest.getInstance("SHA-256");
		objMesssageDigest.update(input.getBytes());
		byte[] byteData = objMesssageDigest.digest();
		StringBuilder strBuffer = new StringBuilder();

		for (byte aByteData : byteData) {
			strBuffer.append(Integer.toString(
					(aByteData & 0xff) + 0x100, 16).substring(1));
		}

		return strBuffer.toString();
	}

	private String generatePassword(String hash) {
		char[] hashArray = hash.toCharArray();
		char[] shortHashArray = new char[12];
		char[] temporaryCharacter = new char[6];

		System.arraycopy(hashArray, 52, shortHashArray, 0, 12);

		StringBuilder password = new StringBuilder();
		int hexNum;
		for (int intCtr = 0; intCtr < 6; intCtr++) {
			temporaryCharacter[0] = shortHashArray[2 * intCtr];
			temporaryCharacter[1] = shortHashArray[2 * intCtr + 1];

			String temporaryString = new String(temporaryCharacter);
			temporaryString = temporaryString.trim();
			hexNum = Integer.parseInt(temporaryString, 16);

			String hexNumString = Integer.toString(hexNum);

			if (password.length() == 5) {
				password.append(hexNumString.charAt(0));
			} else if (password.length() == 4) {
				if (hexNum < 10)
					password.append(hexNumString.charAt(0));
				else {
					password.append(hexNumString.charAt(0));
					password.append(hexNumString.charAt(1));
				}
			} else if (password.length() >= 6) {
				break;
			} else {
				password.append(hexNumString);
			}
		}

		return password.toString().substring(0, 4);
	}

}
