package com.sunrider.bikeparking.services.locationservice;


import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class TaskOnSuccessListener implements OnCompleteListener<LocationSettingsResponse> {

    private final FusedLocationProviderClient mFusedLocationClient;
    private final LocationRequest mLocationRequest;
    private final LocationCallback mLocationCallback;

    public TaskOnSuccessListener(FusedLocationProviderClient mFusedLocationClient,
                                 LocationRequest mLocationRequest, LocationCallback mLocationCallback) {
        this.mFusedLocationClient = mFusedLocationClient;
        this.mLocationRequest = mLocationRequest;
        this.mLocationCallback = mLocationCallback;

    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper());
    }
}
