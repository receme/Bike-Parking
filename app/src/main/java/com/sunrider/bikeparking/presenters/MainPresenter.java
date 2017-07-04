package com.sunrider.bikeparking.presenters;


import com.sunrider.bikeparking.db.DatabaseService;
import com.sunrider.bikeparking.interfaces.MainView;
import com.sunrider.bikeparking.services.firebase.FirebaseService;

public class MainPresenter {

    private final MainView view;
    private final FirebaseService firebase;
    private final DatabaseService dbService;

    public MainPresenter(MainView view, FirebaseService firebase, DatabaseService dbService){
        this.view = view;
        this.firebase = firebase;
        this.dbService = dbService;
    }

    public void init(){
        view.init();
        view.setupNavigationDrawer();
        view.defineClickListener();
    }
}
