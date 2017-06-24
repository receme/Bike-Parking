package com.sunrider.bikeparking.presenters;


import com.sunrider.bikeparking.interfaces.MainView;

public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view){
        this.view = view;
    }

    public void init(){
        view.init();
        view.setupNavigationDrawer();
        view.defineClickListener();

    }
}
