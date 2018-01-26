package com.sunrider.bikeparking.services.locationservice;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sunrider.bikeparking.services.LocationService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationServiceImpl implements LocationService, LocationListener {

    private static final String TAG = LocationServiceImpl.class.getSimpleName();
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    private LocationManager mLocationManager;

    private Activity activity;

    private static LocationServiceImpl instance;
    private LocationListener listener;

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            mCurrentLocation = locationResult.getLastLocation();

            if (listener != null) {
                listener.onLocationFound(mCurrentLocation);
                stopLocationUpdates();
            }

            System.out.println(mCurrentLocation.getLatitude() + " , " + mCurrentLocation.getLongitude());
        }
    };

    public static LocationServiceImpl getInstance(Activity activity) {

        if (instance == null) {
            instance = new LocationServiceImpl(activity);
        }
        return instance;
    }

    @Override
    public void disable(){
        instance = null;
    }

    private LocationServiceImpl(Activity activity) {
        this.activity = activity;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mSettingsClient = LocationServices.getSettingsClient(activity);

        createLocationRequest();
        buildLocationSettingsRequest();
    }

    @Override
    public void startListeningGPSStatus() {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void stopListeningGPSStatus() {
        if(mLocationManager!=null){
            mLocationManager.removeUpdates(this);
        }
    }


    @Override
    public void setLocationListener(LocationListener listener) {
        this.listener = listener;
    }

    private void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }
    
    @Override
    public void startLocationUpdates() {

        if (listener == null) {
            //this should not happen.
            return;
        }

        Task<LocationSettingsResponse> result = mSettingsClient.checkLocationSettings(mLocationSettingsRequest);
        result.addOnFailureListener(new TaskOnFailureListener(activity, listener, REQUEST_CHECK_SETTINGS));
        result.addOnCompleteListener(new TaskOnSuccessListener(mFusedLocationClient,mLocationRequest,mLocationCallback));
    }

    @Override
    public void stopLocationUpdates() {
        try {
            if (mFusedLocationClient != null) {
                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAddress(double lat, double lng) {

        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        String addressLine = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null && addresses.size() > 0) {
                String smallAddress = addresses.get(0).getAddressLine(0);
                String cityName = addresses.get(0).getLocality();
                String stateName = addresses.get(0).getAdminArea();
                String countryName = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                StringBuilder sb = new StringBuilder("");
                if (smallAddress != null) {
                    sb.append(smallAddress);
                }
                if (cityName != null) {
                    sb.append(", ");
                    sb.append(cityName);
                }
                if (stateName != null) {
                    sb.append(", ");
                    sb.append(stateName);
                }
                if (countryName != null) {
                    sb.append(", ");
                    sb.append(countryName);
                }
                if (postalCode != null) {
                    sb.append(", ");
                    sb.append(postalCode);
                }

                addressLine = sb.toString();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressLine;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        System.out.println("GPS status changed");
    }

    @Override
    public void onProviderEnabled(String s) {
        System.out.println("GPS enabled");
        startLocationUpdates();
    }

    @Override
    public void onProviderDisabled(String s) {
        System.out.println("GPS disabled");
        stopLocationUpdates();
    }
}
