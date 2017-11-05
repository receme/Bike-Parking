package com.sunrider.bikeparking.services.googlemap;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.sunrider.bikeparking.R;
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
    public void loadMap() {

        GooglePlayServiceUtils.PlayServiceStatus playServiceStatus = GooglePlayServiceUtils.checkPlayServices(activity);

        if(playServiceStatus.equals(GooglePlayServiceUtils.PlayServiceStatus.DEVICE_NOT_SUPPORTED)){
            AppUtilMethods.showAlert(activity,"",activity.getResources().getString(R.string.device_not_supported),"Ok",null,null);
            return;
        }

        if(playServiceStatus.equals(GooglePlayServiceUtils.PlayServiceStatus.NOT_AVAILABLE)){
            AppUtilMethods.showAlert(activity, "", activity.getResources().getString(R.string.install_play_service), "Ok", "Cancel", new BaseView.AlertViewAction() {
                @Override
                public void onPositiveBtnClicked() {

                    try {
                        activity.startActivityForResult(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=" + "com.google.android.gms")), GooglePlayServiceUtils.REQ_CODE_INSTALL_PLAYSERVICE);
                    } catch (ActivityNotFoundException anfe) {
                        activity.startActivityForResult(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.gms")), GooglePlayServiceUtils.REQ_CODE_INSTALL_PLAYSERVICE);
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
                AppUtilMethods.showAlert(activity,"",activity.getResources().getString(R.string.map_load_fail),"Ok",null,null);
                return;
            }

            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void addMarkers(List<BikeUtilityLocation> markers) {

    }
}
