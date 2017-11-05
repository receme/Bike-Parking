package com.sunrider.bikeparking.utils;


import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class GooglePlayServiceUtils {

    public static final int REQ_CODE_INSTALL_PLAYSERVICE = 901;

    public enum PlayServiceStatus {
        DEVICE_NOT_SUPPORTED, NOT_AVAILABLE, AVAILABLE
    }

    public static PlayServiceStatus checkPlayServices(Activity activity) {

        //final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int resultCode = api.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (api.isUserResolvableError(resultCode)) {
                //api.getErrorDialog((activity), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                return PlayServiceStatus.NOT_AVAILABLE;
            } else {
                return PlayServiceStatus.DEVICE_NOT_SUPPORTED;
            }
        }

        return PlayServiceStatus.AVAILABLE;
    }

}
