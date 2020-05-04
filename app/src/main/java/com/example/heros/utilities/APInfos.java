package com.example.heros.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.example.heros.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class APInfos {

    public static final String CHARACTER_BASE_URL = "http://gateway.marvel.com/v1/public/characters";

    public static final String PUBLIC_KEY = "7a3980224fd49fb87d04c8ae6887a0ac";
    public static final String PRIVATE_KEY = "1bf4af1f01e539dc29246bd5c80aa1ff2d4495bf";

    public static final String TIMESTAMP = "ts";
    public static final String API_KEY = "apikey";
    public static final String HASH = "hash";
    public static final String LIMIT = "limit";


    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());

            return Strings.hexEncode(digest.digest());


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
