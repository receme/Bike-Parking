package com.sunrider.bikeparking.services;


import android.location.Location;

public interface LocationService {

    String getAddress(double lat, double lng);

    void setLocationListener(LocationListener listener);

    void startLocationUpdates();

    void stopLocationUpdates();

    void startListeningGPSStatus();
    void stopListeningGPSStatus();

    void disable();


    interface LocationListener {
        void onLocationFound(Location location);

        void onLocationResolutionSuccess();

        void onLocationResolutionFailed();
    }
}
