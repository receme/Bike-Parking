package com.sunrider.bikeparking.services;


public interface PermissionCheckerService {
    void checkPermission(String permissionType, Listener listener);

    interface Listener{
        void onPermissionGranted();
        void onPermissionDenied();
        void onPermissionRationaleShouldBeShown();
    }

}
