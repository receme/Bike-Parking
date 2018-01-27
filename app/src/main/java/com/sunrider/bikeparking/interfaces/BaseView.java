package com.sunrider.bikeparking.interfaces;


import android.support.annotation.NonNull;

public interface BaseView {

    void showSnackBar(int messageResourceId);

    void showAlert(String title, @NonNull final String message, @NonNull final String positiveBtn, final String negativeBtn, final BaseView.AlertViewAction callback);

    void showAlert(int titleResId, @NonNull final int messageResId, @NonNull final String positiveBtn, final String negativeBtn, final BaseView.AlertViewAction callback);

    void showToastMessage(String message);

    interface AlertViewAction {
        void onPositiveBtnClicked();

        void onNegativeBtnClicked();
    }
}
