package com.crossover.conferencemanagement.di.component;

import com.crossover.conferencemanagement.di.PerActivity;
import com.crossover.conferencemanagement.di.module.ActivityModule;
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.authentication.AuthenticationActivity;
import com.crossover.conferencemanagement.ui.option.OptionActivity;

import dagger.Component;

/**
 * Created by PareshDudhat on 10-03-2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules =
        ActivityModule.class)
public interface ActivityComponent {
    void inject(com.crossover.conferencemanagement.ui.old.MainActivity activity);

    void inject(OptionActivity activity);

    void inject(AuthenticationActivity activity);

    void inject(MainActivity activity);
}
