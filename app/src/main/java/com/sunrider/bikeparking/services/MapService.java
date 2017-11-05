package com.sunrider.bikeparking.services;


import java.util.List;

public interface MapService<T> {
    void loadMap();
    void addMarkers(List<T> markers);

    interface Callback<T> {
        void onMapReady();

        void onMarkerClicked(T vt);

        void onMapClicked();

    }
}
