package com.sunrider.bikeparking.utils;


import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

public class AppUtilMethods {

    public static void showToast(final Activity activity,int resourceId){
        Toast.makeText(activity,activity.getString(resourceId),Toast.LENGTH_SHORT).show();
    }

    public static void showToast(final Activity activity,String message){
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(final Activity activity,final int mainTextStringId, final int actionStringId,
                                    View.OnClickListener listener) {
        Snackbar.make(
                activity.findViewById(android.R.id.content),
                activity.getResources().getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(activity.getString(actionStringId), listener).show();
    }
}
