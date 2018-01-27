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

public class FirebaseAuthManager implements FirebaseAuth.AuthStateListener {

    private Activity activity;
    private FirebaseAuth mAuth;
    private FirebaseAuthStateCallback authStateCallback;

    public FirebaseAuthManager(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);
    }

    public void setAuthStateCallback(final FirebaseAuthStateCallback authStateCallback) {
        this.authStateCallback = authStateCallback;
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

    public void signout() {
        if (mAuth != null) {
            mAuth.signOut();
        }
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if (authStateCallback == null) return;

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            authStateCallback.onLogout();
        }
    }

    public interface Callback {
        void onFirebaseLoginComplete();

        void onFirebaseLoginFailure();
    }

    public interface FirebaseAuthStateCallback {
        void onLogout();
    }

}
