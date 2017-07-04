package com.sunrider.bikeparking.activities;


import android.app.Application;

import com.sunrider.bikeparking.db.DBManager;

public class ApplicationObject extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize database on application start
        DBManager.getInstance(this);
    }
}
