package com.sunrider.bikeparking.fragments;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.activities.MainActivity;
import com.sunrider.bikeparking.db.entities.ParkingLocationEntity;

public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private GoogleMap googleMap;

    private OnFragmentInteractionListener mListener;
    private static HomeFragment instance;

    private Marker locationPickerMarker;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }

        return instance;
    }

    public HomeFragment() {
    }

//    public static HomeFragment newInstance(String param1, String param2) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
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

    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if(((MainActivity)getActivity()).checkPermissions()){
            googleMap.setMyLocationEnabled(true);

        }

        googleMap.setOnMarkerDragListener(this);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);


        mListener.onMapLoadingComplete();
    }

    public void showLocation(Location location) {
        if (googleMap != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));
            googleMap.moveCamera(cameraUpdate);
        }
    }

    public void enableLocationPicker() {

        if(googleMap!=null){
            locationPickerMarker = googleMap.addMarker(new MarkerOptions()
                .position(googleMap.getCameraPosition().target)
                .title("Pick a location").draggable(true));

        }
    }

    public void disableLocationPicker() {

        if(locationPickerMarker!=null){
            locationPickerMarker.remove();
        }

    }

    public ParkingLocationEntity getParkingLocation() {

        if(locationPickerMarker!=null){

            LatLng position = locationPickerMarker.getPosition();
            ParkingLocationEntity parkingLocation = new ParkingLocationEntity();
            parkingLocation.setLat(position.latitude);
            parkingLocation.setLng(position.longitude);

            return parkingLocation;
        }

        return null;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    public interface OnFragmentInteractionListener {
        void onMapLoadingComplete();
    }
}
