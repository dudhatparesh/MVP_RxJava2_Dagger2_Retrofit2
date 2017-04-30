package com.crossover.conferencemanagement.di.component;

import com.crossover.conferencemanagement.di.PerFragment;
import com.crossover.conferencemanagement.di.module.FragmentModule;
import com.crossover.conferencemanagement.ui.main.conferencelist.ConferenceListFragment;
import com.crossover.conferencemanagement.ui.main.editconference.EditConferenceFragment;
import com.crossover.conferencemanagement.ui.main.editsuggestion.EditSuggestionFragment;
import com.crossover.conferencemanagement.ui.main.invites.InvitesFragment;
import com.crossover.conferencemanagement.ui.main.scheduledconferences.ScheduledConfFragment;
import com.crossover.conferencemanagement.ui.main.suggestionlist.SuggestionListFragment;
import com.crossover.conferencemanagement.ui.authentication.login.LoginFragment;
import com.crossover.conferencemanagement.ui.authentication.registration.RegistrationFragment;

import dagger.Component;

/**
 * Created by PareshDudhat on 11-03-2017.
 */
@PerFragment
@Component(dependencies = {ApplicationComponent.class}, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(LoginFragment fragment);

    void inject(RegistrationFragment fragment);

    void inject(ConferenceListFragment fragment);

    void inject(EditConferenceFragment fragment);

    void inject(SuggestionListFragment fragment);

    void inject(EditSuggestionFragment fragment);

    void inject(ScheduledConfFragment fragment);

    void inject(InvitesFragment fragment);
}
