package com.sunrider.bikeparking.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.services.SocialAuthService;
import com.sunrider.bikeparking.services.facebook.FacebookAuthManager;
import com.sunrider.bikeparking.services.firebase.FirebaseAuthManager;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements SocialAuthService.Callback, FirebaseAuthManager.Callback{

    @BindView(R.id.fbLoginBtn)
    Button fbLoginBtn;

    private FirebaseAuthManager firebaseAuthManager;
    private FacebookAuthManager facebookAuthManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuthManager = new FirebaseAuthManager(this);
        facebookAuthManager = new FacebookAuthManager(this,this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                FirebaseUser currentUser = firebaseAuthManager.getCurrentUser();
                updateUI(currentUser);

            }
        }, 3000);

        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                facebookAuthManager.login();
            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {

        if(currentUser == null){
            fbLoginBtn.setVisibility(View.VISIBLE);
        }
        else{
            showHomeScreen();
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
        firebaseAuthManager.signinWithFacebookCredential(authtoken,this);
    }

    @Override
    public void onFacebookLoginFailure() {

    }

    @Override
    public void onFirebaseLoginComplete() {
        showHomeScreen();
    }

    @Override
    public void onFirebaseLoginFailure() {

    }

    private void showHomeScreen(){

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(facebookAuthManager!=null){
            facebookAuthManager.getCallbackManager().onActivityResult(requestCode,resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
