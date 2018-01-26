package com.sunrider.bikeparking.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.interfaces.BaseView;
import com.sunrider.bikeparking.utils.AppUtilMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }

    public abstract int getLayoutResourceId();

    public abstract String getActivityTitle();

    @Override
    public void showSnackBar(int messageResourceId) {

    }

    @Override
    public void showAlert(String title, @NonNull String message, @NonNull String positiveBtn, String negativeBtn, AlertViewAction callback) {
        AppUtilMethods.showAlert(this, title, message, positiveBtn, negativeBtn, callback);
    }

    @Override
    public void showAlert(int titleResId, @NonNull int messageResId, @NonNull String positiveBtn, String negativeBtn, AlertViewAction callback) {

    }

    @Override
    public void showToastMessage(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
