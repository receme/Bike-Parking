package com.sunrider.bikeparking.models;


public class BikeUtilityLocation {

    public enum LocationType {
        Parking, Servicing
    }

    private String locAddress;
    private double lat;
    private double lng;
    private LocationType locationType;

    public BikeUtilityLocation(String locAddress, double lat, double lng, LocationType locationType) {
        this.locAddress = locAddress;
        this.lat = lat;
        this.lng = lng;
        this.locationType = locationType;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
}
