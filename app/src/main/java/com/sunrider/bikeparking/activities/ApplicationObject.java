package com.sunrider.bikeparking.activities;


import android.app.Application;

import com.sunrider.bikeparking.db.DBManager;
import com.sunrider.bikeparking.utils.SharedPrefUtils;

public class ApplicationObject extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefUtils.initialize(this);
    }
}
