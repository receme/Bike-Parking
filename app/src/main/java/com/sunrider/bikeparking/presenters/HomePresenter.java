package com.sunrider.bikeparking.presenters;


import android.location.Location;

import com.sunrider.bikeparking.db.entities.ParkingLocationEntity;
import com.sunrider.bikeparking.interfaces.HomeView;
import com.sunrider.bikeparking.models.BikeUtilityLocation;
import com.sunrider.bikeparking.services.MapService;

public class HomePresenter extends BasePresenter {

    private final HomeView view;
    private final MapService<BikeUtilityLocation> mapService;

    public HomePresenter(final HomeView view, final MapService<BikeUtilityLocation> mapService) {
        this.view = view;
        this.mapService = mapService;
    }

    @Override
    public void init() {
        mapService.loadMap();
    }

    public void showLocation(Location location) {

        if (mapService.isReady()) {
            mapService.showLocation(location);
            setLocationBtnEnabled(true);
        }

    }

    public void setLocationBtnEnabled(boolean isLocationPermissionGiven) {
        mapService.setLocationBtnEnabled(isLocationPermissionGiven);
    }

    public ParkingLocationEntity getSelectedLocation() {

        ParkingLocationEntity locationEntity = mapService.getSelectedLocation();
        return locationEntity;
    }

    public void enableLocationPicker() {
        mapService.enableLocationPicker();
    }

    public void disableLocationPicker() {
        mapService.disableLocationPicker();
    }

}
