package com.sunrider.bikeparking.presenters;


import android.Manifest;
import android.location.Location;

import com.sunrider.bikeparking.db.DatabaseService;
import com.sunrider.bikeparking.interfaces.BaseView;
import com.sunrider.bikeparking.interfaces.MainView;
import com.sunrider.bikeparking.services.LocationService;
import com.sunrider.bikeparking.services.PermissionCheckerService;
import com.sunrider.bikeparking.services.firebase.FirebaseService;

public class MainPresenterImpl implements MainPresenter, LocationService.LocationListener {

    private final MainView view;
    private final FirebaseService firebase;
    private final DatabaseService dbService;
    private final PermissionCheckerService permissionCheckerService;
    private final MainPresenter self;

    private LocationService locationService;

//    private Location location;

    public MainPresenterImpl(MainView view, FirebaseService firebase, DatabaseService dbService, LocationService locationService, PermissionCheckerService permissionCheckerService) {
        this.view = view;
        this.firebase = firebase;
        this.dbService = dbService;
        this.locationService = locationService;
        this.permissionCheckerService = permissionCheckerService;
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
        }

    }

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
