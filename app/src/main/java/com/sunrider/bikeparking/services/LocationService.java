package com.sunrider.bikeparking.services;


import com.sunrider.bikeparking.services.locationservice.LocationServiceImpl;

public interface LocationService {
    void setLocationListener(LocationServiceImpl.LocationListener listener);
    void startLocationUpdates() throws Exception;
}
