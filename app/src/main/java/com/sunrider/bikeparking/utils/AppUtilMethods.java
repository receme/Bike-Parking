package com.sunrider.bikeparking.utils;


import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

public class AppUtilMethods {

    public static void showSnackbar(final Activity activity,final int mainTextStringId, final int actionStringId,
                                    View.OnClickListener listener) {
        Snackbar.make(
                activity.findViewById(android.R.id.content),
                activity.getResources().getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(activity.getString(actionStringId), listener).show();
    }
}
