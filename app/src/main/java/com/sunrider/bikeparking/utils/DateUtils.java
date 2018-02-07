package com.sunrider.bikeparking.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    public static String getCurrentDateTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM ddd, yyyy hh:mm:ss a");
        return dateFormat.format(cal.getTime());
    }
}
