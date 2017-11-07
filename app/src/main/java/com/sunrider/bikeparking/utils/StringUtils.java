package com.sunrider.bikeparking.utils;


import android.app.Activity;

public class StringUtils {

    public static String getString(Activity activity, int resourceId) {
        return activity.getString(resourceId);
    }

    public static boolean isNotNullOrEmpty(String str) {
        if (str != null && str.length() > 0) {
            return true;
        }

        return false;
    }
}
