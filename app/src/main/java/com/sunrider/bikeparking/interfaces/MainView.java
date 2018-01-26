package com.sunrider.bikeparking.interfaces;


import android.location.Location;

public interface MainView extends BaseView {

    void init();
    void setupNavigationDrawer();
    void defineClickListener();
    void loadFragment();
    void showLocationOnMap(Location location);
    void saveAsLastKnownLocation(Location location);

    void openDeviceSettingsPage();

    void setLocationBtnEnabled();
}
