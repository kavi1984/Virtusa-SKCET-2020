package org.launchcode.stocks.models.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class PasswordHash {

    private static final String salt = "asdf";

    public static String getHash(String password){

        String hash = null;
        String saltedPassword = applySalt(password);

        if(null == password) return null;

        try {
            
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedPassword.getBytes(), 0, saltedPassword.length());
            hash = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash;

    }

    private static String applySalt(String password) {
        return password + salt;
    }

    public static boolean isValidPassword(String password, String hash) {

        String hashedPassword = getHash(password);
        return hashedPassword.equals(hash);

    }

}
