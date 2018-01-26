package com.sunrider.bikeparking.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.db.entities.ParkingLocationEntity;
import com.sunrider.bikeparking.interfaces.HomeView;
import com.sunrider.bikeparking.models.BikeUtilityLocation;
import com.sunrider.bikeparking.presenters.HomePresenter;
import com.sunrider.bikeparking.services.MapService;
import com.sunrider.bikeparking.services.googlemap.GoogleMapImpl;
import com.sunrider.bikeparking.utils.AppUtilMethods;
import com.sunrider.bikeparking.utils.GooglePlayServiceUtils;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeView, MapService.Callback<BikeUtilityLocation> {

    private HomePresenter presenter;

    private OnFragmentInteractionListener mListener;
    private Location location;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(this, new GoogleMapImpl(getActivity(), this));

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        //presenter = new HomePresenter(this, new GoogleMapImpl(getActivity(), this));
        presenter.init();

        if (location != null) {
            presenter.showLocation(location);
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GooglePlayServiceUtils.REQ_CODE_INSTALL_PLAYSERVICE) {
            presenter.init();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void showLocation(Location location) {

        this.location = location;
        if (presenter != null) {
            presenter.showLocation(location);

        }
    }

    public void enableLocationPicker() {

//        if (googleMap != null) {
//            locationPickerMarker = googleMap.addMarker(new MarkerOptions()
//                    .position(googleMap.getCameraPosition().target)
//                    .title("Pick a location").draggable(true));
//
//        }
    }

    public void disableLocationPicker() {

//        if (locationPickerMarker != null) {
//            locationPickerMarker.remove();
//        }

    }

    public ParkingLocationEntity getParkingLocation() {

//        if (locationPickerMarker != null) {
//
//            LatLng position = locationPickerMarker.getPosition();
//            ParkingLocationEntity parkingLocation = new ParkingLocationEntity();
//            parkingLocation.setLat(position.latitude);
//            parkingLocation.setLng(position.longitude);
//
//            return parkingLocation;
//        }

        return null;
    }

    @Override
    public void onMapReady() {
        if (this.location != null) {
            presenter.showLocation(this.location);
        }
    }

    @Override
    public void onMarkerClicked(BikeUtilityLocation vt) {

    }

    @Override
    public void onMapClicked() {

    }

    public void setLocationBtnEnabled() {
        presenter.setLocationBtnEnabled(AppUtilMethods.isPermissionGiven(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public interface OnFragmentInteractionListener {
        void onMapLoadingComplete();
    }
}
