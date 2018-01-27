package com.sunrider.bikeparking.services;


import com.facebook.CallbackManager;

public interface SocialAuthService {

    void login();
    CallbackManager getCallbackManager();

    interface Callback{
        void onFacebookLoginSuccess(String authtoken);
        void onFacebookLoginFailure();
    }
}
