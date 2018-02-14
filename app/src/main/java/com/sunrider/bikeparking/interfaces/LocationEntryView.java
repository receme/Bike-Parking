package com.sunrider.bikeparking.interfaces;


import com.sunrider.bikeparking.db.entities.LocationEntity;

public interface LocationEntryView {
    LocationEntity getLocationEntity();

    void setLocationInfo(LocationEntity locationEntity);

    void showLocationOnMapReady();

    void showAlert(String message);

    void onLocationAddedSuccess(LocationEntity location, String message);
}
