package com.epam.library.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypt {
    private final static String ALGORITHM = "MD5";

    private MD5Encrypt() {

    }

    /**
     * @param toBeEncrypted is the text that we want to encrypt it
     * @return encrypted value
     */
    public static String encrypt(String toBeEncrypted) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

            byte[] bytes = toBeEncrypted.getBytes();
            byte[] digested = messageDigest.digest(bytes);


            for (byte b : digested) {
                sb.append(Integer.toHexString(0xff & b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
