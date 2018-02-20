package com.sunrider.bikeparking.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sunrider.bikeparking.R;
import com.wang.avi.AVLoadingIndicatorView;

public class ProgressDialogUtils {

    private static Dialog progressDialog;

    public static void showProgressDialog(Context context, String message) {

        if (progressDialog == null) {
            progressDialog = new Dialog(context, R.style.ProgressViewTheme);

        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.progress_indicator, null);
        AVLoadingIndicatorView progressView = view.findViewById(R.id.progressView);
        TextView progressMessageTv = view.findViewById(R.id.progressMessageTv);

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
}
