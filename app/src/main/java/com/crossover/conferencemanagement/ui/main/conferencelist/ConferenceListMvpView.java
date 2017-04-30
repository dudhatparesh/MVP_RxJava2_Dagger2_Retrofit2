package com.crossover.conferencemanagement.ui.main.conferencelist;

import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.ui.base.MvpView;

import java.util.List;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public interface ConferenceListMvpView extends MvpView {
    void displayEmptyList();

    void displayError(String message);
    void displayError(int message);

    void displayConferences(List<Conference> conferences);
}
