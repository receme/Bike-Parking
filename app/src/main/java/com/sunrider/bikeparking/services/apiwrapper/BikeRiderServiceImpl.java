package com.sunrider.bikeparking.services.apiwrapper;


import okhttp3.ResponseBody;
import retrofit2.Call;

public class BikeRiderServiceImpl implements BikeRiderApi{
    @Override
    public Call<ResponseBody> login(String username, String password) {
        return null;
    }
}
