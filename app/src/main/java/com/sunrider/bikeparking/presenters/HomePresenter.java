package com.sunrider.bikeparking.presenters;


import android.location.Location;

import com.sunrider.bikeparking.db.entities.ParkingLocationEntity;
import com.sunrider.bikeparking.interfaces.HomeView;
import com.sunrider.bikeparking.models.BikeUtilityLocation;
import com.sunrider.bikeparking.services.LocationService;
import com.sunrider.bikeparking.services.MapService;

public class HomePresenter extends BasePresenter {

    private final HomeView view;
    private final MapService<BikeUtilityLocation> mapService;
    private final LocationService locationService;

    public HomePresenter(final HomeView view, final MapService<BikeUtilityLocation> mapService, final LocationService locationService) {
        this.view = view;
        this.mapService = mapService;
        this.locationService = locationService;
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

    public void onLocationSelectedToAdd(double latitude, double longitude) {

        String address = locationService.getAddress(latitude,longitude);
        view.showAddressOfSelectedLocation(address);
    }
}
