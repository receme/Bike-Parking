package com.sunrider.bikeparking.interfaces;


import android.location.Location;

import com.sunrider.bikeparking.db.entities.LocationEntity;

import java.util.List;

public interface MainView extends BaseView {

    void init();
    void setupNavigationDrawer();
    void defineClickListener();
    void loadFragment();
    void showLocationOnMap(Location location);
    void saveAsLastKnownLocation(Location location);

    void openDeviceSettingsPage();

    void setLocationBtnEnabled();

    void showLocationEntitiesOnMap(List<LocationEntity> locationEntity);
}
