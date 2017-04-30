package com.crossover.conferencemanagement.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.crossover.conferencemanagement.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PareshDudhat on 11-03-2017.
 */
@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ActivityContext
    public Context provideContext() {
        return fragment.getContext();
    }
    @Provides
    public Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    public Fragment provideFragment() {
        return fragment;
    }
}
