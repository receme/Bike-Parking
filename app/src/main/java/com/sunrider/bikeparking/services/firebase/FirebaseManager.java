package com.sunrider.bikeparking.services.firebase;


import android.app.Activity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager implements FirebaseService{

    private Activity activity;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseServiceListener listener;

    public FirebaseManager(Activity activity) {
        this.activity = activity;
    }

    public void setListener(FirebaseServiceListener listener) {
        this.listener = listener;
    }

    @Override
    public void initializeDatabase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("bikeparking");
    }

    @Override
    public void addValue(Object value) {
        if(databaseReference!=null){
            databaseReference.setValue(value, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError!=null && listener!=null){
                        listener.onDatabaseError(databaseError.getMessage());
                    }
                }
            });
        }
    }


    public interface FirebaseServiceListener{
        void onDatabaseError(String message);
    }
}
