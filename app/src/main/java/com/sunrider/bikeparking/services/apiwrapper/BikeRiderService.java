package com.sunrider.bikeparking.services.apiwrapper;


import okhttp3.ResponseBody;

public interface BikeRiderService {


    void addUser(String userid, String name, String accountCreatedAt, RequestListener<ResponseBody> listener);

    void getUser(String userid, RequestListener<ResponseBody> listener);

    void getUserContributions(String userid, RequestListener<ResponseBody> listener);

    void addLocation(String name, String lat, String lng, String type, String comment, String updatedAt, String userid, RequestListener<ResponseBody> listener);

    void updateLocation(String id, String name, String lat, String lng, String type, String comment, String updatedAt, String userid, RequestListener<ResponseBody> listener);

    void removeLocation(String userid, RequestListener<ResponseBody> listener);

    void reportLocation(String location_id, String reason, String id, String createdAt, RequestListener<ResponseBody> listener);

    void getLocations(double lat, double lng, double radius, RequestListener<ResponseBody> listener);
}
