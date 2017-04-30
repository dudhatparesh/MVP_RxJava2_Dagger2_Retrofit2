package com.crossover.conferencemanagement.ui.authentication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.authentication.login.LoginFragment;
import com.crossover.conferencemanagement.ui.authentication.registration.RegistrationFragment;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class AuthenticationPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public AuthenticationPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a LoginFragment or RegistrationFragment.
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegistrationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.login);
            case 1:
                return context.getString(R.string.registration);
        }
        return null;
    }
}
