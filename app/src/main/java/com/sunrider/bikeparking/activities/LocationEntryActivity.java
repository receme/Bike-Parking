package com.sunrider.bikeparking.activities;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.interfaces.LocationEntryView;
import com.sunrider.bikeparking.presenters.LocationEntryPresenter;
import com.sunrider.bikeparking.services.apiwrapper.BikeRiderServiceImpl;
import com.sunrider.bikeparking.utils.AppUtilMethods;
import com.sunrider.bikeparking.utils.DateUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationEntryActivity extends BaseActivity implements LocationEntryView, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    @BindView(R.id.addressEdtxt)
    EditText addressEdtxt;
    @BindView(R.id.commentEdtxt)
    EditText commentEdtxt;
    @BindView(R.id.locationTypeRG)
    RadioGroup locationTypeRG;
    @BindView(R.id.bikeParkingRB)
    RadioButton bikeParkingRB;
    @BindView(R.id.bikeServicingRB)
    RadioButton bikeServicingRB;

    private Marker marker;
    private LocationEntryPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new LocationEntryPresenter(this, new BikeRiderServiceImpl(getString(R.string.api_base_url)));
        presenter.init();

        addressEdtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (marker != null) {
                    marker.setTitle(charSequence.toString());
                    marker.showInfoWindow();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_addlocation;
    }

    @Override
    public String getActivityTitle() {
        return "Add Location";
    }

    @Override
    public LocationEntity getLocationEntity() {

        Parcelable wrappedLocation = getIntent().getParcelableExtra("Location");
        LocationEntity locationEntity = Parcels.unwrap(wrappedLocation);

        return locationEntity;
    }

    @Override
    public void setLocationInfo(LocationEntity locationEntity) {
        addressEdtxt.setText(locationEntity.getAddress());
        addressEdtxt.setSelection(addressEdtxt.getText().toString().length());
    }

    @Override
    public void showLocationOnMapReady() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            AppUtilMethods.showAlert(this, "", getResources().getString(R.string.map_load_fail), "Ok", null, null);
            return;
        }


        int[] screenWidthHeight = AppUtilMethods.getScreenSize(this);
        int height = screenWidthHeight[1];
        mapFragment.getView().getLayoutParams().height = height / 4;

        mapFragment.getView().setClickable(false);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void showAlert(String message) {
        AppUtilMethods.showAlert(this, "", message, "Ok", null, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {

            googleMap.setOnMapClickListener(this);

            LocationEntity entity = presenter.getLocationEntity();
            LatLng position = new LatLng(entity.getLat(), entity.getLng());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f));

            UiSettings settings = googleMap.getUiSettings();
            settings.setMapToolbarEnabled(false);
            settings.setAllGesturesEnabled(false);

            marker = googleMap.addMarker(
                    new MarkerOptions().position(position)
                            .title(addressEdtxt.getText().toString()));
            marker.showInfoWindow();

            position = new LatLng(entity.getLat() + 0.0020, entity.getLng());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        marker.showInfoWindow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_done) {

            LocationEntity location = presenter.getLocationEntity();
            location.setAddress(addressEdtxt.getText().toString());
            location.setComment(commentEdtxt.getText().toString());
            location.setType(getLocationType());
            location.setUpdated_at(DateUtils.getCurrentDateTime());

            presenter.addLocation(location);
        }
        return true;
    }

    String getLocationType() {
        int radioButtonID = locationTypeRG.getCheckedRadioButtonId();
        View radioButton = locationTypeRG.findViewById(radioButtonID);
        int btnIndex = locationTypeRG.indexOfChild(radioButton);
        if (btnIndex == 0) {
            return LocationEntity.LocationType.PARKING.toString();
        } else {
            return LocationEntity.LocationType.SERVICING.toString();
        }
    }


}
