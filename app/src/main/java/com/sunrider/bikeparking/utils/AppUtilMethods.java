package com.sunrider.bikeparking.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.interfaces.BaseView;
import com.wang.avi.AVLoadingIndicatorView;

public class AppUtilMethods {

    private static Dialog progressDialog;

    public static void showProgressDialog(Context context, String message) {

        if (progressDialog == null) {
            progressDialog = new Dialog(context, R.style.ProgressViewTheme);

        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.progress_indicator,null);
        AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) view.findViewById(R.id.progressView);
        TextView progressMessageTv = (TextView) view.findViewById(R.id.progressMessageTv);

        progressMessageTv.setText(message);
        progressView.show();
        progressDialog.setContentView(view);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public static void hideProgressDialog() {

        if (progressDialog != null) {

            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public static void showToast(final Activity activity,int resourceId){
        Toast.makeText(activity,activity.getString(resourceId),Toast.LENGTH_SHORT).show();
    }

    public static void showToast(final Activity activity,String message){
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(final Activity activity,final int mainTextStringId, final int actionStringId,
                                    View.OnClickListener listener) {
        Snackbar.make(
                activity.findViewById(android.R.id.content),
                activity.getResources().getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(activity.getString(actionStringId), listener).show();

    }

    public static void showAlert(final Activity activity, final String title, @NonNull final String message, @NonNull final String positiveBtn, final String negativeBtn, final BaseView.AlertViewAction callback) {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                hideProgressDialog();

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                if (StringUtils.isNotNullOrEmpty(title)) {
                    builder.setTitle(title);
                }

                if (StringUtils.isNotNullOrEmpty(message)) {
                    builder.setMessage(message);
                }

                if (StringUtils.isNotNullOrEmpty(positiveBtn)) {
                    builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (callback != null) {
                                callback.onPositiveBtnClicked();
                            }
                        }
                    });
                }

                if (StringUtils.isNotNullOrEmpty(negativeBtn)) {
                    builder.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (callback != null) {
                                callback.onNegativeBtnClicked();
                            }
                        }
                    });
                }

                builder.setCancelable(false);
                builder.create();
                builder.show();

            }
        });

    }

    public static boolean isStartWithCapitalLetters(final String inputStr){

        if(!StringUtils.isNotNullOrEmpty(inputStr)){
            return false;
        }

        return inputStr.matches("\\b[A-Z]\\b");
    }

    public static boolean isPermissionGiven(Context context,String permission){
        int result = ContextCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static int[] getScreenSize(Activity activity){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        return new int[]{width,height};
    }
}
