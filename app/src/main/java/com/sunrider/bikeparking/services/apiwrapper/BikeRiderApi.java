package com.sunrider.bikeparking.services.apiwrapper;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BikeRiderApi {

    @POST("/api/adduser")
    Call<ResponseBody> addUser(@Body String userid, @Body String address, @Body String profileimg, @Body String accountCreatedAt);

    @FormUrlEncoded
    @POST("/api/getUser")
    Call<ResponseBody> getUser(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("/api/contributions")
    Call<ResponseBody> getUserContributions(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("/api/addlocation")
    Call<ResponseBody> addLocation(@Field("address") String address, @Field("lat") String lat, @Field("lng") String lng,
                                   @Field("type") String type, @Field("comment") String comment, @Field("updated_at") String updatedAt, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("/api/updatelocation")
    Call<ResponseBody> updateLocation(@Field("id") String id, @Field("address") String address, @Field("lat") String lat, @Field("lng") String lng,
                                      @Field("type") String type, @Field("comment") String comment, @Field("updated_at") String updatedAt, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("/api/removelocation")
    Call<ResponseBody> removeLocation(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("/api/reportlocation")
    Call<ResponseBody> reportLocation(@Field("location_id") String location_id, @Field("reason") String reason,
                                      @Field("reported_by") String id, @Field("created_at") String createdAt);

    @FormUrlEncoded
    @GET("/api/locations")
    Call<ResponseBody> getLocations(@Field("userid") String userid);

}
