package com.crossover.conferencemanagement.ui.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.main.conferencelist.ConferenceListFragment;
import com.crossover.conferencemanagement.ui.main.suggestionlist.SuggestionListFragment;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class AdminPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public AdminPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ConferenceListFragment();
            case 1:
                return new SuggestionListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.conference);
            case 1:
                return context.getString(R.string.suggestion);
        }
        return "";
    }
}
