package com.crossover.conferencemanagement.ui.authentication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.base.BaseActivity;

import butterknife.BindView;

public class AuthenticationActivity extends BaseActivity {

    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        AuthenticationPagerAdapter mAuthenticationPagerAdapter = new AuthenticationPagerAdapter(this,
                getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mAuthenticationPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_authentication;
    }
}
