package com.sunrider.bikeparking.presenters;


import android.util.Log;

import com.google.gson.Gson;
import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.interfaces.LocationEntryView;
import com.sunrider.bikeparking.services.apiwrapper.BikeRiderService;
import com.sunrider.bikeparking.services.apiwrapper.RequestListener;
import com.sunrider.bikeparking.services.apiwrapper.responses.AddLocationResponse;
import com.sunrider.bikeparking.utils.StringUtils;

import java.io.IOException;

import okhttp3.ResponseBody;

public class LocationEntryPresenter extends BasePresenter {

    private final LocationEntryView view;
    private final BikeRiderService service;
    private LocationEntity locationEntity;


    public LocationEntryPresenter(final LocationEntryView view, final BikeRiderService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void init() {
        LocationEntity locationEntity = view.getLocationEntity();

        if (locationEntity == null) {
            return;
        }

        this.locationEntity = locationEntity;
        view.setLocationInfo(locationEntity);
        view.showLocationOnMapReady();
    }

    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public void addLocation(LocationEntity location) {

        if (StringUtils.isNullOrEmpty(location.getAddress())) {
            view.showAlert("Please add a name for this place.");
            return;
        }

        service.addLocation(location.getAddress(), Double.toString(location.getLat()), Double.toString(location.getLng()), location.getType(),
                location.getComment(), location.getUpdated_at(), location.getUserid(), new RequestListener<ResponseBody>() {
                    @Override
                    public void onResponseSuccess(ResponseBody response) {

                        if (response == null) {
                            view.showAlert("Something went wrong. Please try again later.");
                            return;
                        }

                        Gson gson = new Gson();
                        try {
                            AddLocationResponse addLocationResponse = gson.fromJson(response.string(), AddLocationResponse.class);

                            if (addLocationResponse.isSuccess()) {
                                view.onLocationAddedSuccess(addLocationResponse.getLocation(), "Location is added successfully.");
                            } else {
                                view.showAlert("Location entry request is not successful. Please try again later.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onResponseFailure(Throwable t) {
                        if (t != null) {
                            Log.v("ERROR", t.getMessage());
                            t.printStackTrace();
                        }
                    }
                });

    }

}
