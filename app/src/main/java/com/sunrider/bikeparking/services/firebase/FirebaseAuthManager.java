package com.sunrider.bikeparking.services.firebase;


import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthManager {

    private Activity activity;
    private FirebaseAuth mAuth;

    public FirebaseAuthManager(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
    }

    public void signinWithFacebookCredential(String facebookAuthToken, final Callback callback) {

        AuthCredential credential = FacebookAuthProvider.getCredential(facebookAuthToken);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            if (callback != null) {
                                callback.onFirebaseLoginComplete();
                            }
                        } else {
                            if (callback != null) {
                                callback.onFirebaseLoginFailure();
                            }
                        }
                    }
                });
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public interface Callback {
        void onFirebaseLoginComplete();

        void onFirebaseLoginFailure();
    }

}
