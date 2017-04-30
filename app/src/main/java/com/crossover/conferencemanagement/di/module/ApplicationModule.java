package com.crossover.conferencemanagement.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.crossover.conferencemanagement.di.ApplicationContext;
import com.crossover.conferencemanagement.di.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by PareshDudhat on 10-03-2017.
 */
@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @ApplicationContext
    public Context provideContext() {
        return mApplication;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    public String provideDatabaseName() {
        return "conference.db";
    }

    @Provides
    @DatabaseInfo
    public int provideDatabaseVersion() {
        return 2;
    }

    @Provides
    public SharedPreferences provideSharedPreferences() {
        return mApplication.getSharedPreferences("pref_conference", Context.MODE_PRIVATE);
    }

    @Provides
    public LocalBroadcastManager providesBroadcastManager(){
        return LocalBroadcastManager.getInstance(mApplication);
    }
}
