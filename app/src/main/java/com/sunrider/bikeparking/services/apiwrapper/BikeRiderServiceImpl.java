package com.sunrider.bikeparking.services.apiwrapper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BikeRiderServiceImpl implements BikeRiderService {

    private final BikeRiderApi api;

    public BikeRiderServiceImpl(String baseUrl) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
        okHttp.connectTimeout(60, TimeUnit.SECONDS);
        okHttp.readTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = okHttp.addNetworkInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(BikeRiderApi.class);

    }

    @Override
    public void addUser(String userid, String name, String profileimg, String accountCreatedAt, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.addUser(userid, name, profileimg, accountCreatedAt);
        call.enqueue(new RequestCallback<>(listener));
    }

    @Override
    public void getUser(String userid, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.getUser(userid);
        call.enqueue(new RequestCallback<>(listener));
    }

    @Override
    public void getUserContributions(String userid, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.getUserContributions(userid);
        call.enqueue(new RequestCallback<>(listener));
    }

    @Override
    public void addLocation(String name, String lat, String lng, String type, String comment, String updatedAt, String userid, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.addLocation(name, lat, lng, type, comment, updatedAt, userid);
        call.enqueue(new RequestCallback<>(listener));
    }

    @Override
    public void updateLocation(String id, String name, String lat, String lng, String type, String comment, String updatedAt, String userid, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.updateLocation(id,name,lat,lng,type,comment,updatedAt,userid);
        call.enqueue(new RequestCallback<>(listener));
    }

    @Override
    public void removeLocation(String userid, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.removeLocation(userid);
        call.enqueue(new RequestCallback<>(listener));
    }

    @Override
    public void reportLocation(String location_id, String reason, String id, String createdAt, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.reportLocation(location_id,reason,id,createdAt);
        call.enqueue(new RequestCallback<>(listener));
    }

    @Override
    public void getLocations(String userid, RequestListener<ResponseBody> listener) {
        Call<ResponseBody> call = api.removeLocation(userid);
        call.enqueue(new RequestCallback<>(listener));
    }
}
