/*
 * Project: UniBss
 * Copyright 2006 Asisinfo   Co. Ltd.
 * All right reserved.
 */

package com.ai.wocloud.system.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encoder {

    private Md5Encoder() {
    }

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F' };

    private static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }


    public static String encodePassword(String rawPass) {

        MessageDigest messageDigest = getMessageDigest();

        byte[] digest = messageDigest.digest(rawPass.getBytes());

        return new String(encodeHex(digest));
    }

    protected final static MessageDigest getMessageDigest() throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + "MD5" + "]");
        }
    }

    public static boolean isPasswordValid(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encodePassword(rawPass);

        return pass1.equals(pass2);
    }


    public static void main(String[] args) {
        String result = Md5Encoder.encodePassword("000000");
        System.out.println("password=" + result);

        boolean isGood = Md5Encoder.isPasswordValid("E10ADC3949BA59ABBE56E057F20F883E", "123456");
        System.out.println("isGood=" + isGood);
    }

}
