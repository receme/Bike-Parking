package com.sunrider.bikeparking.presenters;


import android.location.Location;

import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.interfaces.HomeView;
import com.sunrider.bikeparking.services.LocationService;
import com.sunrider.bikeparking.services.MapService;

import java.util.List;

public class HomePresenter extends BasePresenter {

    private final HomeView view;
    private final MapService<LocationEntity> mapService;
    private final LocationService locationService;

    public HomePresenter(final HomeView view, final MapService<LocationEntity> mapService, final LocationService locationService) {
        this.view = view;
        this.mapService = mapService;
        this.locationService = locationService;
    }

    @Override
    public void init() {
        view.defineClickListener();
        mapService.loadMap();
    }

    public void showLocation(Location location) {

        if (mapService.isReady()) {
            mapService.showLocation(location);
            setLocationBtnEnabled(true);
        }
    }

    public void showLocationEntitiesOnMap(List<LocationEntity> locationEntities) {

        if(locationEntities==null || locationEntities.size() ==0){
            view.showToastMessage("No bikerider location is found around you.");
            return;
        }

        if (mapService.isReady() && locationEntities != null && locationEntities.size() > 0) {
            mapService.addMarkers(locationEntities);
        }


    }

    public void setLocationBtnEnabled(boolean isLocationPermissionGiven) {
        mapService.setLocationBtnEnabled(isLocationPermissionGiven);
    }

    public LocationEntity getSelectedLocation() {

        LocationEntity locationEntity = mapService.getSelectedLocation();
        return locationEntity;
    }

    public void enableLocationPicker() {
        mapService.enableLocationPicker();
    }

    public void disableLocationPicker() {
        mapService.disableLocationPicker();
    }

    public void onLocationSelectedToAdd(double latitude, double longitude) {

        String address = locationService.getAddress(latitude, longitude);
        view.showAddressOfSelectedLocation(address);
    }

}
