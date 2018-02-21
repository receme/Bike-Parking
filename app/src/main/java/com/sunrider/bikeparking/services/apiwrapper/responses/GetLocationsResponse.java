package com.sunrider.bikeparking.services.apiwrapper.responses;


import com.sunrider.bikeparking.db.entities.LocationEntity;

import java.util.List;

public class GetLocationsResponse extends BaseResponse {

    private int count;
    private List<LocationEntity> pickedLocation;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LocationEntity> getPickedLocation() {
        return pickedLocation;
    }

    public void setPickedLocation(List<LocationEntity> pickedLocation) {
        this.pickedLocation = pickedLocation;
    }
}
