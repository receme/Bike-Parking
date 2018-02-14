package com.sunrider.bikeparking.presenters;


import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.interfaces.LocationEntryView;
import com.sunrider.bikeparking.services.apiwrapper.BikeRiderService;
import com.sunrider.bikeparking.services.apiwrapper.RequestListener;
import com.sunrider.bikeparking.utils.StringUtils;

import okhttp3.ResponseBody;

public class LocationEntryPresenter extends BasePresenter {

    private final LocationEntryView view;
    private final BikeRiderService service;
    private LocationEntity locationEntity;


    public LocationEntryPresenter(final LocationEntryView view,final BikeRiderService service) {
        this.view = view;
        this.service = service;
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

    public void addLocation(LocationEntity location) {
        if(StringUtils.isNotNullOrEmpty(location.getAddress())){
            view.showAlert("Please add a name for this place.");
            return;
        }

        service.addLocation(location.getAddress(), Double.toString(location.getLat()), Double.toString(location.getLng()), location.getType(),
                location.getComment(), location.getUpdated_at(), location.getUserid(), new RequestListener<ResponseBody>() {
            @Override
            public void onResponseSuccess(ResponseBody response) {
                if(response == null)
                    return;




            }

            @Override
            public void onResponseFailure(Throwable t) {

            }
        });

    }

}
