package com.aman.tsechackathon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * User: Aman
 * Date: 21-09-2019
 * Time: 05:14 AM
 */
public class HelperClass {
    private static final String TAG = "HelperClass";

    public static Gson getGsonParser() {
        Gson gson;
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
        return gson;
    }
}
