package com.crossover.conferencemanagement.ui.main.adminhome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.main.AdminPagerAdapter;
import com.crossover.conferencemanagement.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class AdminHomeFragment extends BaseFragment {
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_admin_home, container, false);
            ButterKnife.bind(this, view);
            AdminPagerAdapter adapter = new AdminPagerAdapter(getContext(),
                    getChildFragmentManager());
            mViewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(mViewPager);
        }
        return view;
    }
}
