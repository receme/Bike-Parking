package com.sunrider.bikeparking.activities.helper;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.sunrider.bikeparking.activities.NavigationDrawerManager;
import com.sunrider.bikeparking.fragments.AboutFragment;
import com.sunrider.bikeparking.fragments.ContributionFragment;
import com.sunrider.bikeparking.fragments.HomeFragment;
import com.sunrider.bikeparking.fragments.InstructionFragment;
import com.sunrider.bikeparking.fragments.PrivacyPolicyFragment;
import com.sunrider.bikeparking.fragments.SettingsFragment;

public class AppFragmentManager {

    public static Fragment getFragment(AppCompatActivity activity, int navIndex){

        switch (NavigationDrawerManager.navItemIndex) {
            case 0:
                HomeFragment homeFragment = (HomeFragment) activity.getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
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
                return null;
        }
    }
}
