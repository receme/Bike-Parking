package com.sunrider.bikeparking.services.apiwrapper;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BikeRiderApi {

    @FormUrlEncoded
    @POST("/api/adduser")
    Call<ResponseBody> addUser(@Field("userid") String userid, @Field("name") String name, @Field("profileimg") String profileimg, @Field("account_created_at") String accountCreatedAt);

    @FormUrlEncoded
    @POST("/api/getUser")
    Call<ResponseBody> getUser(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("/api/contributions")
    Call<ResponseBody> getUserContributions(@Field("userid") String userid);



}
