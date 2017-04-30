package com.crossover.conferencemanagement.ui.main.doctorhome;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.main.invites.InvitesFragment;
import com.crossover.conferencemanagement.ui.main.scheduledconferences.ScheduledConfFragment;
import com.crossover.conferencemanagement.ui.main.suggestionlist.SuggestionListFragment;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public class DoctorPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public DoctorPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ScheduledConfFragment();
            case 1:
                return new InvitesFragment();
            case 2:
                return new SuggestionListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.schedule);
            case 1:
                return context.getString(R.string.invites);
            case 2:
                return context.getString(R.string.suggestion);
        }
        return "";
    }
}
