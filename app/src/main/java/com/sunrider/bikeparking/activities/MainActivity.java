package com.sunrider.bikeparking.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sunrider.bikeparking.R;
import com.sunrider.bikeparking.fragments.AboutFragment;
import com.sunrider.bikeparking.fragments.ContributionFragment;
import com.sunrider.bikeparking.fragments.HomeFragment;
import com.sunrider.bikeparking.fragments.InstructionFragment;
import com.sunrider.bikeparking.fragments.PrivacyPolicyFragment;
import com.sunrider.bikeparking.fragments.SettingsFragment;
import com.sunrider.bikeparking.interfaces.MainView;
import com.sunrider.bikeparking.presenters.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView{

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private View navHeader;
    private ImageView imgNavHeaderBg;


    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private NavigationDrawerManager navigationDrawerManager;
    private MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.init();

        if (savedInstanceState == null) {
            navigationDrawerManager.setNavItemIndex(0);
            navigationDrawerManager.setCurrentTag(NavigationDrawerManager.TAG_HOME);
            loadFragment();
        }
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
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);

        navigationDrawerManager = new NavigationDrawerManager(this,navigationView,drawer,navHeader,imgNavHeaderBg);

    }

    @Override
    public void setupNavigationDrawer() {

        loadNavHeader();
        setUpNavigationView();
    }

    private void loadNavHeader() {

        Glide.with(this).asDrawable()
                .load(R.drawable.nav_menu_header_bg)
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

        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void defineClickListener() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    @Override
    public void loadFragment() {

        navigationDrawerManager.selectNavMenu(NavigationDrawerManager.navItemIndex);
        navigationDrawerManager.setToolbarTitle(getSupportActionBar());

        if (getSupportFragmentManager().findFragmentByTag(NavigationDrawerManager.CURRENT_TAG) != null) {
            navigationDrawerManager.closeDrawer();
            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, NavigationDrawerManager.CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        navigationDrawerManager.closeDrawer();

        invalidateOptionsMenu();
    }

    private Fragment getFragment() {

        switch (NavigationDrawerManager.navItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                ContributionFragment contributionFragment = new ContributionFragment();
                return contributionFragment;

            case 2:
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            case 3:
                InstructionFragment instructionFragment = new InstructionFragment();
                return instructionFragment;
            case 4:
                PrivacyPolicyFragment privacyPolicyFragment = new PrivacyPolicyFragment();
                return privacyPolicyFragment;
            case 5:
                AboutFragment aboutFragment = new AboutFragment();
                return aboutFragment;

            default:
                return new HomeFragment();
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

        if (NavigationDrawerManager.navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
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
}
