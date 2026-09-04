package com.c2.lc.lib.utils;

import java.io.IOException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DigitalSignature {

    public static Map getSecretKeys() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
           // System.out.println(kpg.getAlgorithm());
            ECGenParameterSpec ecsp = new ECGenParameterSpec("secp256r1");
            kpg.initialize(ecsp); //ecsp
            KeyPair kyp = kpg.genKeyPair();
            PublicKey pubKey = kyp.getPublic();
            PrivateKey privKey = kyp.getPrivate();
            Base64.Encoder encoder = Base64.getEncoder();
          //  System.out.println("privateKey: " + encoder.encodeToString(privKey.getEncoded()));
          //  System.out.println("publicKey: " + encoder.encodeToString(pubKey.getEncoded()));
            Map<String,String>    keyMap = new HashMap<String,String>();
            keyMap.put("public-key",encoder.encodeToString(pubKey.getEncoded()));
            keyMap.put("private-key",encoder.encodeToString(privKey.getEncoded()));
            return keyMap;
    }


    public static String getDigitalySignedContent(String intent,String privKey) throws GeneralSecurityException, IOException {
            PrivateKey privateKey = (PrivateKey) loadPublicKey(privKey);
            //Signature with Sha-256
            Signature dsa = Signature.getInstance("SHA256withECDSA");
            dsa.initSign(privateKey);
            byte[] strByte = intent.getBytes("UTF-8");
            dsa.update(strByte);
            //Sign with private key
            byte[] realSig = dsa.sign();
            //Encode signed URL with base 64
            String  encodedSign = Base64.getEncoder().encodeToString(realSig);
          //  System.out.println("\nSignature: " + new BigInteger(1, realSig).toString(16));
          //  System.out.println("\nEncoded Signature:" + encodedSign);
        return encodedSign;
    }

    public static Key loadPublicKey(String stored) throws GeneralSecurityException, IOException
    {
        byte[] data = Base64.getDecoder().decode((stored.getBytes()));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(data);
        KeyFactory fact = KeyFactory.getInstance("EC");
        PrivateKey privKey =   fact.generatePrivate(spec);
        //System.out.println("privateKey2: " +  Base64.getEncoder().encodeToString(privKey.getEncoded()));
        return privKey;
    }

}



