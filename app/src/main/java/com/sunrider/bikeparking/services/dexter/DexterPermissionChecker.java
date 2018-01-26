package com.sunrider.bikeparking.services.dexter;


import android.app.Activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sunrider.bikeparking.services.PermissionCheckerService;

import java.util.List;

public class DexterPermissionChecker implements PermissionCheckerService {

    private final Activity activity;

    public DexterPermissionChecker(final Activity activity ) {
        this.activity = activity;
    }

    @Override
    public void checkPermission(String permissionType,final Listener listener) {

        Dexter.withActivity(activity).withPermission(permissionType).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                listener.onPermissionGranted();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                listener.onPermissionDenied();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                listener.onPermissionRationaleShouldBeShown();
            }
        }).check();
    }
}
