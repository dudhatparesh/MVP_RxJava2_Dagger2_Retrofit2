package com.crossover.conferencemanagement.di.module;

import android.app.Activity;
import android.content.Context;

import com.crossover.conferencemanagement.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

@Module
public class ActivityModule {
    private Activity mActivity;
    public ActivityModule(Activity activity){
        this.mActivity=activity;
    }
    @Provides
    @ActivityContext
    public Context provideContext(){
        return mActivity;
    }
    @Provides
    public Activity provideActivity(){
        return mActivity;
    }
}
