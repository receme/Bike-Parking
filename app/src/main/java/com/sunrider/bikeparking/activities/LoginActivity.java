package com.sunrider.bikeparking.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.services.SocialAuthService;
import com.sunrider.bikeparking.services.apiwrapper.BikeRiderServiceImpl;
import com.sunrider.bikeparking.services.apiwrapper.RequestListener;
import com.sunrider.bikeparking.services.apiwrapper.responses.AddUserResponse;
import com.sunrider.bikeparking.services.facebook.FacebookAuthManager;
import com.sunrider.bikeparking.services.firebase.FirebaseAuthManager;
import com.sunrider.bikeparking.utils.DateUtils;
import com.sunrider.bikeparking.utils.IntentExtras;
import com.sunrider.bikeparking.utils.PreferenceConstants;
import com.sunrider.bikeparking.utils.SharedPrefUtils;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.ResponseBody;

public class LoginActivity extends BaseActivity implements SocialAuthService.Callback, FirebaseAuthManager.Callback {

    @BindView(R.id.fbLoginBtn)
    Button fbLoginBtn;

    private FirebaseAuthManager firebaseAuthManager;
    private FacebookAuthManager facebookAuthManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuthManager = new FirebaseAuthManager(this);
        facebookAuthManager = new FacebookAuthManager(this, this);
        facebookAuthManager.init();

        boolean waitingNeeded = getIntent().getBooleanExtra("WaitingNeeded", true);
        int delayTime = 3000;

        if (!waitingNeeded) {
            delayTime = 0;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = firebaseAuthManager.getCurrentUser();
                updateUI(currentUser);

            }
        }, delayTime);

        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbLoginBtn.setClickable(false);
                facebookAuthManager.login();

            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            fbLoginBtn.setVisibility(View.VISIBLE);
        } else {
            showHomeScreen(currentUser);
        }
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public String getActivityTitle() {
        return "";
    }

    @Override
    public void onFacebookLoginSuccess(String authtoken) {
        firebaseAuthManager.signinWithFacebookCredential(authtoken, this);
    }

    @Override
    public void onFacebookLoginFailure() {
        fbLoginBtn.setClickable(true);
    }

    @Override
    public void onFirebaseLoginComplete(final FirebaseUser user) {

        SharedPrefUtils.getInstance().putString(PreferenceConstants.USER_UID, user.getUid());

        new BikeRiderServiceImpl(getString(R.string.api_base_url)).addUser(user.getUid(), user.getDisplayName(), DateUtils.getCurrentDateTime(),
                new RequestListener<ResponseBody>() {
                    @Override
                    public void onResponseSuccess(ResponseBody response) {

                        if(response == null){
                            showAlert("","Login failed.","Ok",null,null);
                            return;
                        }

                        Gson gson = new Gson();

                        try {
                            AddUserResponse addUserResponse = gson.fromJson(response.string(),AddUserResponse.class);
                            if(addUserResponse == null){
                                showAlert("","Login failed.","Ok",null,null);
                                return;
                            }

                            if(addUserResponse.isSuccess()){
                                showHomeScreen(user);
                            }
                            else{
                                showAlert("","Login failed. Try again later","Ok",null,null);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert("","Login failed. Try again later","Ok",null,null);
                        }


                    }

                    @Override
                    public void onResponseFailure(Throwable t) {
                        t.printStackTrace();
                        showAlert("","Login failed. Try again later","Ok",null,null);
                    }
                });



    }

    @Override
    public void onFirebaseLoginFailure() {
        fbLoginBtn.setClickable(true);
    }

    private void showHomeScreen(FirebaseUser user) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(IntentExtras.NAME, user.getDisplayName());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (facebookAuthManager != null) {
            facebookAuthManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
