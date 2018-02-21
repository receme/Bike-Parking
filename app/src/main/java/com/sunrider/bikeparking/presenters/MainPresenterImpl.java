package com.sunrider.bikeparking.presenters;


import android.Manifest;
import android.location.Location;

import com.google.gson.Gson;
import com.sunrider.bikeparking.db.DatabaseService;
import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.interfaces.BaseView;
import com.sunrider.bikeparking.interfaces.MainView;
import com.sunrider.bikeparking.services.LocationService;
import com.sunrider.bikeparking.services.PermissionCheckerService;
import com.sunrider.bikeparking.services.apiwrapper.BikeRiderService;
import com.sunrider.bikeparking.services.apiwrapper.RequestListener;
import com.sunrider.bikeparking.services.apiwrapper.responses.GetLocationsResponse;
import com.sunrider.bikeparking.services.firebase.FirebaseService;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

public class MainPresenterImpl implements MainPresenter, LocationService.LocationListener {

    private final MainView view;
    private final FirebaseService firebase;
    private final PermissionCheckerService permissionCheckerService;
    private final MainPresenter self;
    private final BikeRiderService service;

    private LocationService locationService;

    public MainPresenterImpl(MainView view, FirebaseService firebase,  LocationService locationService,
                             PermissionCheckerService permissionCheckerService, BikeRiderService service) {
        this.view = view;
        this.firebase = firebase;
        this.locationService = locationService;
        this.permissionCheckerService = permissionCheckerService;
        this.service = service;
        this.self = this;
    }

    public void init() {

        locationService.setLocationListener(this);

        view.init();
        view.setupNavigationDrawer();
        view.defineClickListener();

    }

    public void checkLocationPermission() {

        permissionCheckerService.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, new PermissionCheckerService.Listener() {
            @Override
            public void onPermissionGranted() {
                startUpdatingLocation();
            }

            @Override
            public void onPermissionDenied() {
                view.showAlert(null, "Without Location permission app cannot find the nearest parking or service center.", "Ok", null, null);
            }

            @Override
            public void onPermissionRationaleShouldBeShown() {
                view.showAlert(null, "Location permission is needed to find nearest parking or service center. Change the permission in device settings page.", "Ok", "Cancel", new BaseView.AlertViewAction() {
                    @Override
                    public void onPositiveBtnClicked() {
                        view.openDeviceSettingsPage();
                    }

                    @Override
                    public void onNegativeBtnClicked() {

                    }
                });
            }
        });
    }

    public void startUpdatingLocation() {
        view.setLocationBtnEnabled();
        locationService.startLocationUpdates();
        locationService.startListeningGPSStatus();
    }

    @Override
    public void onLocationFound(Location location) {

        if (location != null) {
            view.showLocationOnMap(location);
            view.saveAsLastKnownLocation(location);

            service.getLocations(location.getLatitude(), location.getLongitude(), 5, new RequestListener<ResponseBody>() {
                @Override
                public void onResponseSuccess(ResponseBody response) {

                    if (response == null) {
                        view.showAlert("", "An error occurred when app was trying to fetch data. Refresh the app to fix the problem.",
                                "Ok", null, null);
                        return;
                    }


                    try {
                        Gson gson = new Gson();
                        GetLocationsResponse getLocationsResponse = gson.fromJson(response.string(), GetLocationsResponse.class);

                        if (getLocationsResponse == null) {
                            view.showAlert("", "An error occurred when app was trying to fetch data. Refresh the app to fix the problem.",
                                    "Ok", null, null);
                            return;
                        }

                        if (getLocationsResponse.isSuccess()) {
                            List<LocationEntity> locationEntities = getLocationsResponse.getPickedLocation();
                            view.showLocationEntitiesOnMap(locationEntities);
                        }
                        else{
                            view.showAlert("", "An error occurred when app was trying to fetch data. Refresh the app to fix the problem.",
                                    "Ok", null, null);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponseFailure(Throwable t) {

                    t.printStackTrace();

                    view.showAlert("", "An error occurred when app was trying to fetch data. Refresh the app to fix the problem.",
                            "Ok", null, null);
                }
            });

        }

    }

//    public void showLocationEntitiesFromDB(){
//
//        List<LocationEntity> locationEntities = dbService.getAllParkingLocation();
//
//        if(locationEntities==null){
//            return;
//        }
//
//        view.showLocationEntitiesOnMap(locationEntities);
//    }

    @Override
    public void onLocationResolutionSuccess() {

    }

    @Override
    public void onLocationResolutionFailed() {
        checkLocationPermission();
    }

    public void stopLocationUpdates() {

        if (locationService != null) {

            locationService.stopLocationUpdates();
            locationService.stopListeningGPSStatus();
            locationService.disable();

        }
    }

}
