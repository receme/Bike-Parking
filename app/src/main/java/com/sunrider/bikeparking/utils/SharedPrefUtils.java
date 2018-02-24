package com.sunrider.bikeparking.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefUtils {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static SharedPrefUtils instance;

    public static void initialize(Context context) {

        if (instance == null) {
            instance = new SharedPrefUtils(context);
        }
    }

    public static SharedPrefUtils getInstance(){
        return instance;
    }

    private SharedPrefUtils(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
    }

    public static void removeKey(String key){
        editor.remove(key);
        editor.apply();
    }

    public static void putInt(String key, int val)
    {
        editor.putInt(key, val);
        editor.apply();
    }

    public static int getInt(String key, int defaultValue)
    {

        return sp.getInt(key, defaultValue);

    }

    public static void putString(String key, String val)
    {
        editor.putString(key, val);
        editor.apply();
    }

    public static String getString(String key, String defaultValue)
    {
        return sp.getString(key, defaultValue);
    }

    public static void putBool(String key, boolean val)
    {
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static boolean getBool(String key, boolean defaultValue)
    {
        return sp.getBoolean(key, defaultValue);
    }
}
