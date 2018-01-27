package com.sunrider.bikeparking.services.facebook;


import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.sunrider.bikeparking.services.SocialAuthService;

import java.util.Arrays;

public class FacebookAuthManager implements SocialAuthService {

    private LoginManager loginManager;
    private CallbackManager callbackManager;
    private Activity activity;
    private SocialAuthService.Callback callback;

    public FacebookAuthManager(Activity activity, final Callback callback) {

        this.activity = activity;
        this.callback = callback;
        loginManager = LoginManager.getInstance();
        loginManager.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        callbackManager = CallbackManager.Factory.create();

    }

    public void init() {

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (callback != null) {
                    callback.onFacebookLoginSuccess(loginResult.getAccessToken().getToken());
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                if (callback != null) {
                    callback.onFacebookLoginFailure();
                }
            }
        });
    }

    @Override
    public void login() {
        if(loginManager!=null){
            loginManager.logInWithReadPermissions(activity, Arrays.asList("public_profile"));
        }
    }

    @Override
    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
