package com.sunrider.bikeparking.activities;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.activities.helper.AppFragmentManager;
import com.sunrider.bikeparking.activities.helper.RequestCode;
import com.sunrider.bikeparking.db.DBManager;
import com.sunrider.bikeparking.db.entities.LocationEntity;
import com.sunrider.bikeparking.fragments.HomeFragment;
import com.sunrider.bikeparking.interfaces.MainView;
import com.sunrider.bikeparking.presenters.MainPresenterImpl;
import com.sunrider.bikeparking.services.apiwrapper.BikeRiderServiceImpl;
import com.sunrider.bikeparking.services.dexter.DexterPermissionChecker;
import com.sunrider.bikeparking.services.firebase.FirebaseAuthManager;
import com.sunrider.bikeparking.services.firebase.FirebaseManager;
import com.sunrider.bikeparking.services.locationservice.LocationServiceImpl;
import com.sunrider.bikeparking.utils.AppUtilMethods;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView, HomeFragment.OnFragmentInteractionListener,
        FirebaseManager.FirebaseServiceListener, FirebaseAuthManager.FirebaseAuthStateCallback {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private View navHeader;
    private ImageView imgNavHeaderBg;

    private Handler mHandler;

    private NavigationDrawerManager navigationDrawerManager;
    private MainPresenterImpl presenter;

    private FirebaseAuthManager firebaseAuthManager;

    //state variables
    private boolean shouldLoadHomeFragOnBackPress = true;
    private boolean isMapLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        presenter = new MainPresenterImpl(
                this,
                new FirebaseManager(this),
                DBManager.getInstance(this),
                LocationServiceImpl.getInstance(this),
                new DexterPermissionChecker(this),
                new BikeRiderServiceImpl(getString(R.string.api_base_url)));
        presenter.init();


        if (savedInstanceState == null) {
            navigationDrawerManager.setNavItemIndex(0);
            navigationDrawerManager.setCurrentTag(NavigationDrawerManager.TAG_HOME);
            loadFragment();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopLocationUpdates();
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public void init() {

        mHandler = new Handler();
        navHeader = navigationView.getHeaderView(0);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        navigationDrawerManager = new NavigationDrawerManager(this, navigationView, drawer, navHeader, imgNavHeaderBg);


        firebaseAuthManager = new FirebaseAuthManager(this);
        firebaseAuthManager.setAuthStateCallback(this);
    }

    @Override
    public void setupNavigationDrawer() {

        loadNavHeader();
        setUpNavigationView();
    }

    private void loadNavHeader() {

        Glide.with(this).asDrawable()
                .load(R.mipmap.nav_menu_header_bg)
                .into(imgNavHeaderBg);

        navigationDrawerManager.setActionView(3, R.layout.menu_dot);
    }

    private void setUpNavigationView() {

        navigationDrawerManager.setUpNavigationView();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void defineClickListener() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(NavigationDrawerManager.TAG_HOME);
                if(homeFragment == null){
                    return;
                }

                homeFragment.enableLocationPicker();
                fab.setVisibility(View.GONE);
            }
        });
    }

    public void onSelectLocation(){

        fab.setVisibility(View.VISIBLE);

        final HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(NavigationDrawerManager.TAG_HOME);
        if(homeFragment == null){
            return;
        }

        final LocationEntity location = homeFragment.getLocationEntity();

        if(location == null){
            AppUtilMethods.showToast(MainActivity.this,"Location cannot be determined. Please try again.");
            return;
        }

        Intent intent = new Intent(MainActivity.this, LocationEntryActivity.class);
        Parcelable wrappedLocation = Parcels.wrap(location);
        intent.putExtra("Location", wrappedLocation);
        startActivityForResult(intent, RequestCode.ADD_LOCATION);
    }

    public void onCancelLocationSelection(){
        fab.setVisibility(View.VISIBLE);
    }

    public void loadFragment() {

        if(NavigationDrawerManager.navItemIndex == 0){
            presenter.checkLocationPermission();
        }

        navigationDrawerManager.selectNavMenu(NavigationDrawerManager.navItemIndex);
        navigationDrawerManager.setToolbarTitle(getSupportActionBar());

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = AppFragmentManager.getFragment(MainActivity.this);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, NavigationDrawerManager.CURRENT_TAG);
                fragmentTransaction.commit();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        toggleFab();
        navigationDrawerManager.closeDrawer();

    }

    @Override
    public void showLocationOnMap(Location location) {

        HomeFragment homeFragment = (HomeFragment) AppFragmentManager.getFragment(this, NavigationDrawerManager.TAG_HOME);
        if (homeFragment != null) {
            homeFragment.showLocation(location);
        }
    }

    @Override
    public void saveAsLastKnownLocation(Location location) {

    }

    @Override
    public void openDeviceSettingsPage() {

        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void setLocationBtnEnabled() {
        HomeFragment homeFragment = (HomeFragment) AppFragmentManager.getFragment(this, NavigationDrawerManager.TAG_HOME);
        if (homeFragment != null) {
            homeFragment.setLocationBtnEnabled();
        }
    }

    @Override
    public void showLocationEntitiesOnMap(List<LocationEntity> locationEntity) {
        HomeFragment homeFragment = (HomeFragment) AppFragmentManager.getFragment(this, NavigationDrawerManager.TAG_HOME);
        if (homeFragment != null) {
            homeFragment.showLocationEntitiesOnMap(locationEntity);
        }
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if (shouldLoadHomeFragOnBackPress) {

            if (NavigationDrawerManager.navItemIndex != 0) {
                navigationDrawerManager.setNavItemIndex(0);
                navigationDrawerManager.setCurrentTag(NavigationDrawerManager.TAG_HOME);
                loadFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {

            showAlert("", "Are you sure to logout?", "Yes", "No", new AlertViewAction() {
                @Override
                public void onPositiveBtnClicked() {
                    firebaseAuthManager.signout();
                }

                @Override
                public void onNegativeBtnClicked() {

                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleFab() {
        if (NavigationDrawerManager.navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    @Override
    public void onMapLoadingComplete() {
        isMapLoaded = true;
    }

    @Override
    public void onDatabaseError(String message) {
        AppUtilMethods.showToast(this, message);
    }

    @Override
    public void onLogout() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("WaitingNeeded", false);
        startActivity(intent);

        finish();
    }
}
