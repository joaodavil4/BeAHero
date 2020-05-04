package com.example.heros.utilities;

public class Strings {

    private static char[] HEXCHARS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    static String hexEncode(byte[] bytes) {
        char[] result = new char[bytes.length*2];
        int b;
        for (int i = 0, j = 0; i < bytes.length; i++) {
            b = bytes[i] & 0xff;
            result[j++] = HEXCHARS[b >> 4];
            result[j++] = HEXCHARS[b & 0xf];
        }
        return new String(result);
    }

}
