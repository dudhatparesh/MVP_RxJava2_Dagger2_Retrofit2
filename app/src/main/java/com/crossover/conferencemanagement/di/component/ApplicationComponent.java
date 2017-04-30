package com.crossover.conferencemanagement.di.component;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.crossover.conferencemanagement.MainApplication;
import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.DbHelper;
import com.crossover.conferencemanagement.data.PrefHelper;
import com.crossover.conferencemanagement.di.ApplicationContext;
import com.crossover.conferencemanagement.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(MainApplication mainApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    PrefHelper getPrefeHelper();

    DbHelper getDBHelper();
    LocalBroadcastManager getLocalBroadcastManager();
}
