package com.sunrider.bikeparking.presenters;


import com.sunrider.bikeparking.interfaces.MainView;
import com.sunrider.bikeparking.services.firebase.FirebaseService;

public class MainPresenter {

    private final MainView view;
    private final FirebaseService firebase;

    public MainPresenter(MainView view, FirebaseService firebase){
        this.view = view;
        this.firebase = firebase;
    }

    public void init(){
        view.init();
        view.setupNavigationDrawer();
        view.defineClickListener();
    }
}
