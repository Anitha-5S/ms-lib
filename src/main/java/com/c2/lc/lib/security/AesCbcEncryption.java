package com.c2.lc.lib.security;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class AesCbcEncryption {

    public String getEncryptedData(String plainText, String key, String IV) throws BadPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        return encrypt(plainText,key, IV);
    }

    public String getDecryptedData(String decryptedText,String key, String IV) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        return decrypt(decryptedText,key, IV);
    }

    private String encrypt (String plaintext, String key, String IV ) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(key), getIvParameterSpec(IV));
        byte[] cipherText = cipher.doFinal(plaintext.getBytes());
        return Base64.encodeBase64String(cipherText);
    }

    private String decrypt (String cipherText, String key,String IV) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = getCipher();
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(key), getIvParameterSpec(IV));
        byte[] decryptedText = Base64.decodeBase64(cipherText);
        return new String(cipher.doFinal(decryptedText));
    }

    private Cipher getCipher () throws NoSuchPaddingException, NoSuchAlgorithmException { return Cipher.getInstance("AES/CBC/PKCS5Padding"); }

    private SecretKeySpec getSecretKeySpec (String key) throws UnsupportedEncodingException {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    }

    private IvParameterSpec getIvParameterSpec (String IV) throws UnsupportedEncodingException {
        return new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
    }

}
