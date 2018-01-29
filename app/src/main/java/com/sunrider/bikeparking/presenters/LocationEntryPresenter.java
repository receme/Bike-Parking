package com.sunrider.bikeparking.presenters;


import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.interfaces.LocationEntryView;

public class LocationEntryPresenter extends BasePresenter {

    private final LocationEntryView view;
    private LocationEntity locationEntity;

    public LocationEntryPresenter(LocationEntryView view) {
        this.view = view;
    }

    @Override
    public void init() {
        LocationEntity locationEntity = view.getLocationEntity();

        if(locationEntity==null){
            return;
        }

        this.locationEntity = locationEntity;
        view.setLocationInfo(locationEntity);
        view.showLocationOnMapReady();
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }
}
