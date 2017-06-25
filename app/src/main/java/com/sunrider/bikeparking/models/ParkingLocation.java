package com.sunrider.bikeparking.models;


public class ParkingLocation {

    private long id;
    private String address;
    private double lat;
    private double lng;
    private String comment;

    public ParkingLocation(String address, double lat, double lng, String comment) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.comment = comment;
    }

    public ParkingLocation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
