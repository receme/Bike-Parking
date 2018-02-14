package com.sunrider.bikeparking.services.apiwrapper.responses;


import com.sunrider.bikeparking.db.entities.LocationEntity;

public class AddLocationResponse extends BaseResponse {

    private LocationEntity location;

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
