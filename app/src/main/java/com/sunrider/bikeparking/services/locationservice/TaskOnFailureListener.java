package com.sunrider.bikeparking.services.locationservice;


import android.app.Activity;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;

public class TaskOnFailureListener implements OnFailureListener {

    private final Activity activity;
    private final LocationServiceImpl.LocationListener listener;
    private final int requestCode;

    public TaskOnFailureListener(Activity activity, LocationServiceImpl.LocationListener listener, int requestCode) {
        this.activity = activity;
        this.listener = listener;
        this.requestCode = requestCode;
    }

    @Override
    public void onFailure(@NonNull Exception e) {

        int statusCode = ((ApiException) e).getStatusCode();
        switch (statusCode) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the
                    // result in onActivityResult().
                    ResolvableApiException rae = (ResolvableApiException) e;
                    rae.startResolutionForResult(activity, requestCode);

                    listener.onLocationResolutionSuccess();
                } catch (IntentSender.SendIntentException sie) {
                    //Log.i(TAG, "PendingIntent unable to execute request.");
                    listener.onLocationResolutionFailed();
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                String errorMessage = "Location settings are inadequate, and cannot be " +
                        "fixed here. Fix in Settings.";
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
