package com.sunrider.bikeparking.services.apiwrapper;

public interface RequestListener<T> {
    void onResponseSuccess(T response);
    void onResponseFailure(Throwable t);
}