package com.sunrider.bikeparking.services;


public interface SocialAuthService {

    void login(Callback callback);

    interface Callback{
        void onLoginSuccess(String authtoken);
        void onLoginFailure();
    }
}
