package com.sunrider.bikeparking.activities;


import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.interfaces.MainView;

public class NavigationDrawerManager {

    private MainView mainView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;

    public static int navItemIndex = 0;

    public static final String TAG_HOME = "home";
    public static final String TAG_CONTRIBUTIONS = "contributions";
    public static final String TAG_SETTINGS = "settings";
    public static final String TAG_INSTRUCTIONS = "instructions";
    public static final String TAG_PRIVACY_POLICY = "privacy_policy";
    public static final String TAG_ABOUT = "about";
    public static String CURRENT_TAG = TAG_HOME;

    public NavigationDrawerManager(MainView mainView, NavigationView navigationView, DrawerLayout drawer, View navHeader, ImageView imgNavHeaderBg) {
        this.mainView = mainView;
        this.navigationView = navigationView;
        this.drawer = drawer;
        this.navHeader = navHeader;
        this.imgNavHeaderBg = imgNavHeaderBg;
    }

    public void setActionView(int itemPosition, int layoutId) {

        if (navigationView != null) {
            navigationView.getMenu().getItem(itemPosition).setActionView(layoutId);
        }
    }

    public void closeDrawer() {

        if (drawer != null) {
            drawer.closeDrawers();
        }
    }

    public void selectNavMenu(int navItemIndex) {
        if (navigationView != null) {
            navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        }
    }

    public void setUpNavigationView() {

        if (navigationView == null) {
            return;
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_contribution:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CONTRIBUTIONS;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_instruction:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_INSTRUCTIONS;
                        break;
                    case R.id.nav_privacy_policy:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_PRIVACY_POLICY;
                        break;
                    case R.id.nav_about:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ABOUT;
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                if(mainView!=null){
                    mainView.loadHomeFragment();
                }

                return true;
            }
        });

    }

    public void setNavItemIndex(int index) {
        this.navItemIndex = index;
    }

    public void setCurrentTag(String tag) {
        this.CURRENT_TAG = tag;
    }

    public int getNavItemIndex() {
        return navItemIndex;
    }

    public String getCurrentTag() {
        return CURRENT_TAG;
    }
}
