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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.activities.MainActivity;
import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.interfaces.HomeView;
import com.sunrider.bikeparking.models.BikeUtilityLocation;
import com.sunrider.bikeparking.presenters.HomePresenter;
import com.sunrider.bikeparking.services.MapService;
import com.sunrider.bikeparking.services.googlemap.GoogleMapImpl;
import com.sunrider.bikeparking.services.locationservice.LocationServiceImpl;
import com.sunrider.bikeparking.utils.AppUtilMethods;
import com.sunrider.bikeparking.utils.GooglePlayServiceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeView, MapService.Callback<BikeUtilityLocation> {

    @BindView(R.id.locationPickerMarker)
    ImageView locationPickerMarker;
    @BindView(R.id.selectedLocationView)
    LinearLayout selectedLocationView;
    @BindView(R.id.searchEdtxt)
    EditText searchEdtxt;
    @BindView(R.id.addressTv)
    TextView addressTv;
    @BindView(R.id.okBtn)
    Button okBtn;
    @BindView(R.id.cancelBtn)
    Button cancelBtn;

    private HomePresenter presenter;

    private OnFragmentInteractionListener mListener;
    private Location location;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new HomePresenter(this,
                new GoogleMapImpl(getActivity(), this),
                LocationServiceImpl.getInstance(getActivity()));

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
        searchEdtxt.setText("");
        addressTv.setText("");

        locationPickerMarker.setVisibility(View.VISIBLE);
        selectedLocationView.setVisibility(View.VISIBLE);

        presenter.enableLocationPicker();
    }

    public void disableLocationPicker() {
        locationPickerMarker.setVisibility(View.GONE);
        selectedLocationView.setVisibility(View.GONE);

        presenter.disableLocationPicker();
    }

    public LocationEntity getLocationEntity() {
        LocationEntity position = presenter.getSelectedLocation();
        position.setAddress(addressTv.getText().toString());
        return position;
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

    @Override
    public void onLocationSelectedToAdd(double latitude, double longitude) {
        presenter.onLocationSelectedToAdd(latitude, longitude);
    }

    public void setLocationBtnEnabled() {
        presenter.setLocationBtnEnabled(AppUtilMethods.isPermissionGiven(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @Override
    public void defineClickListener() {
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).onSelectLocation();
                disableLocationPicker();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).onCancelLocationSelection();
                disableLocationPicker();
            }
        });
    }

    @Override
    public void showAddressOfSelectedLocation(String address) {
        if(address!=null) {
            addressTv.setText(address);
        }
    }

    public interface OnFragmentInteractionListener {
        void onMapLoadingComplete();
    }
}
