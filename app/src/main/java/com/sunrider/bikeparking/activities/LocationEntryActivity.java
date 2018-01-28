package com.sunrider.bikeparking.activities;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.utils.AppUtilMethods;

import org.parceler.Parcels;

public class LocationEntryActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_addlocation);

        Parcelable wrappedLocation =  getIntent().getParcelableExtra("Location");
        LocationEntity locationEntity = Parcels.unwrap(wrappedLocation);

        AppUtilMethods.showAlert(this,locationEntity.getLat()+","+locationEntity.getLng(), locationEntity.getAddress(),"Ok",null,null);
    }
}
