package com.crossover.conferencemanagement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by PareshDudhat on 15-03-2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        OptionPresenterTest.class,
        LoginPresenterTest.class,
        RegistrationPresenterTest.class,
        ConferenceListPresenterTest.class,
        SuggestionListPresenterTest.class,
        ScheduleListPresenterTest.class,
        EditConferencePresenterTest.class,
        EditSuggestionPresenterTest.class
})
public class MainTestClass {
}
