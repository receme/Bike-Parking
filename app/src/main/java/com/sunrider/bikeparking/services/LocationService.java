package com.sunrider.bikeparking.services;


import android.location.Location;

public interface LocationService {
    void setLocationListener(LocationListener listener);

    void startLocationUpdates();

    void stopLocationUpdates();


    interface LocationListener {
        void onLocationFound(Location location);

        void onLocationResolutionSuccess();

        void onLocationResolutionFailed();
    }
}
