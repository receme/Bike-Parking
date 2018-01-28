package com.sunrider.bikeparking.services.googlemap;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.db.entities.ParkingLocationEntity;
import com.sunrider.bikeparking.interfaces.BaseView;
import com.sunrider.bikeparking.models.BikeUtilityLocation;
import com.sunrider.bikeparking.services.MapService;
import com.sunrider.bikeparking.utils.AppUtilMethods;
import com.sunrider.bikeparking.utils.GooglePlayServiceUtils;

import java.util.List;

public class GoogleMapImpl implements MapService<BikeUtilityLocation>, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private Activity activity;
    private GoogleMap googleMap;
    private Fragment fragment;
    private Callback callback;
    private List<BikeUtilityLocation> listOfBikeUtilityLocations;


    public GoogleMapImpl(Activity activity, @NonNull Fragment fragment) {

        this.activity = activity;
        this.fragment = fragment;
        this.callback = (Callback) fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        UiSettings settings = googleMap.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setZoomControlsEnabled(false);
        settings.setZoomGesturesEnabled(true);
        settings.setMapToolbarEnabled(false);

        LatLng latLng = new LatLng(23.94, 90.34);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));

        callback.onMapReady();

        googleMap.setOnMarkerClickListener(this);
        //googleMap.setOnMapClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public boolean isReady() {
        return googleMap != null;
    }

    @Override
    public void loadMap() {

        GooglePlayServiceUtils.PlayServiceStatus playServiceStatus = GooglePlayServiceUtils.checkPlayServices(activity);

        if (playServiceStatus.equals(GooglePlayServiceUtils.PlayServiceStatus.DEVICE_NOT_SUPPORTED)) {
            AppUtilMethods.showAlert(activity, "", activity.getResources().getString(R.string.device_not_supported), "Ok", null, null);
            return;
        }

        if (playServiceStatus.equals(GooglePlayServiceUtils.PlayServiceStatus.NOT_AVAILABLE)) {
            AppUtilMethods.showAlert(activity, "", activity.getResources().getString(R.string.install_play_service), "Ok", "Cancel", new BaseView.AlertViewAction() {
                @Override
                public void onPositiveBtnClicked() {

                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=" + "com.google.android.gms"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivityForResult(intent, GooglePlayServiceUtils.REQ_CODE_INSTALL_PLAYSERVICE);
                    } catch (ActivityNotFoundException anfe) {

                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.gms"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivityForResult(intent, GooglePlayServiceUtils.REQ_CODE_INSTALL_PLAYSERVICE);

                    }

                }

                @Override
                public void onNegativeBtnClicked() {

                }
            });

            return;
        }

        if (googleMap == null) {

            SupportMapFragment mapFragment = (SupportMapFragment) fragment.getChildFragmentManager().findFragmentById(R.id.map);

            if (mapFragment == null) {
                AppUtilMethods.showAlert(activity, "", activity.getResources().getString(R.string.map_load_fail), "Ok", null, null);
                return;
            }

            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void addMarkers(List<BikeUtilityLocation> markers) {

    }


    @Override
    public void showLocation(Location location) {

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12));

    }

    @SuppressLint("MissingPermission")
    @Override
    public void setLocationBtnEnabled(boolean isLocationPermissionGiven) {

        if (isLocationPermissionGiven) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void enableLocationPicker() {
        if (googleMap != null) {

            LatLng midLatLng = googleMap.getCameraPosition().target;
            Toast.makeText(activity,midLatLng.latitude+" , "+midLatLng.longitude,Toast.LENGTH_SHORT).show();

            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {
                    LatLng midLatLng = googleMap.getCameraPosition().target;
                    //Toast.makeText(activity,midLatLng.latitude+" , "+midLatLng.longitude,Toast.LENGTH_SHORT).show();
                    callback.onLocationSelectedToAdd(midLatLng.latitude, midLatLng.longitude);
                }
            });


        }
    }

    @Override
    public void disableLocationPicker() {
        if (googleMap != null) {
            googleMap.setOnCameraIdleListener(null);
        }
    }

    @Override
    public ParkingLocationEntity getSelectedLocation() {

        if(googleMap!=null){
            LatLng midLatLng = googleMap.getCameraPosition().target;
            ParkingLocationEntity parkingLocation = new ParkingLocationEntity();
            parkingLocation.setLat(midLatLng.latitude);
            parkingLocation.setLng(midLatLng.longitude);

            return parkingLocation;
        }

        return null;
    }
}
