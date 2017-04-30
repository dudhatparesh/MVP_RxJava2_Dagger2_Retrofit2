package com.crossover.conferencemanagement;

import android.app.Application;
import android.content.Context;

import com.crossover.conferencemanagement.di.component.ApplicationComponent;
import com.crossover.conferencemanagement.di.component.DaggerApplicationComponent;
import com.crossover.conferencemanagement.di.module.ApplicationModule;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

public class MainApplication extends Application {
    protected ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
