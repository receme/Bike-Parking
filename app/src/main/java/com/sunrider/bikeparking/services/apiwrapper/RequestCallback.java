package com.sunrider.bikeparking.services.apiwrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallback<T> implements Callback<T> {

    private RequestListener<T> listener;

    public RequestCallback(RequestListener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        listener.onResponseSuccess(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        listener.onResponseFailure(t);
    }
}