package com.sunrider.bikeparking.services;


import android.location.Location;

import java.util.List;

public interface MapService<T> {
    boolean isReady();
    void loadMap();
    void addMarkers(List<T> markers);
    void showLocation(Location location);

    void setLocationBtnEnabled(boolean isLocationPermissionGiven);


    interface Callback<T> {
        void onMapReady();

        void onMarkerClicked(T vt);

        void onMapClicked();

    }
}
