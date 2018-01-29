package com.sunrider.bikeparking.activities;


import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.fragments.AboutFragment;
import com.sunrider.bikeparking.fragments.ContributionFragment;
import com.sunrider.bikeparking.fragments.HomeFragment;
import com.sunrider.bikeparking.fragments.InstructionFragment;
import com.sunrider.bikeparking.fragments.PrivacyPolicyFragment;
import com.sunrider.bikeparking.fragments.SettingsFragment;
import com.sunrider.bikeparking.interfaces.MainView;

public class NavigationDrawerManager {

    private MainView mainView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg;

    public static int navItemIndex = 0;

    public static final String TAG_HOME = HomeFragment.class.getSimpleName();
    public static final String TAG_CONTRIBUTIONS = ContributionFragment.class.getSimpleName();
    public static final String TAG_SETTINGS = SettingsFragment.class.getSimpleName();
    public static final String TAG_INSTRUCTIONS = InstructionFragment.class.getSimpleName();
    public static final String TAG_PRIVACY_POLICY = PrivacyPolicyFragment.class.getSimpleName();
    public static final String TAG_ABOUT = AboutFragment.class.getSimpleName();
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;

    public NavigationDrawerManager(MainView mainView, NavigationView navigationView, DrawerLayout drawer, View navHeader, ImageView imgNavHeaderBg) {
        this.mainView = mainView;
        this.navigationView = navigationView;
        this.drawer = drawer;
        this.navHeader = navHeader;
        this.imgNavHeaderBg = imgNavHeaderBg;

        activityTitles = ((MainActivity)mainView).getResources().getStringArray(R.array.nav_item_activity_titles);
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
                    case R.id.nav_home:
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
                    mainView.loadFragment();
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

    public void setToolbarTitle(ActionBar supportActionBar) {

        supportActionBar.setTitle(activityTitles[NavigationDrawerManager.navItemIndex]);
    }
}
