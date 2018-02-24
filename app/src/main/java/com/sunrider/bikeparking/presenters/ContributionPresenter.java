package com.sunrider.bikeparking.presenters;


import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.services.apiwrapper.BikeRiderService;

import java.util.List;

public class ContributionPresenter {

    private final BikeRiderService service;

    public ContributionPresenter(final BikeRiderService service) {
        this.service = service;
    }

    public List<LocationEntity> getUserContributions(String userId){

        return null;
    }
}
