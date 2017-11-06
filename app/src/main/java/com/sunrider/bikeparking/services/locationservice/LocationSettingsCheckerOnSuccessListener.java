package com.sunrider.bikeparking.services.locationservice;


import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationSettingsCheckerOnSuccessListener implements OnSuccessListener<LocationSettingsResponse> {

    private int locationUpdateCount;
    private final FusedLocationProviderClient mFusedLocationClient;
    private final LocationRequest mLocationRequest;
    private final LocationCallback mLocationCallback;

    public LocationSettingsCheckerOnSuccessListener(int locationUpdateCount, FusedLocationProviderClient mFusedLocationClient,
                                                    LocationRequest mLocationRequest, LocationCallback mLocationCallback) {
        this.locationUpdateCount = locationUpdateCount;
        this.mFusedLocationClient = mFusedLocationClient;
        this.mLocationRequest = mLocationRequest;
        this.mLocationCallback = mLocationCallback;

    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
        locationUpdateCount = 0;
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper());
    }
}
