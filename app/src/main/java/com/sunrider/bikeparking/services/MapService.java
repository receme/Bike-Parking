package com.sunrider.bikeparking.services;


import android.location.Location;

import com.sunrider.bikeparking.db.entities.ParkingLocationEntity;

import java.util.List;

public interface MapService<T> {
    boolean isReady();
    void loadMap();
    void addMarkers(List<T> markers);
    void showLocation(Location location);

    void setLocationBtnEnabled(boolean isLocationPermissionGiven);

    void enableLocationPicker();
    void disableLocationPicker();
    ParkingLocationEntity getSelectedLocation();


    interface Callback<T> {
        void onMapReady();

        void onMarkerClicked(T vt);

        void onMapClicked();

    }
}
