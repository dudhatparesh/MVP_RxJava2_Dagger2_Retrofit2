package com.crossover.conferencemanagement.ui.main.scheduledconferences;

import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.ui.base.MvpView;

import java.util.List;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public interface ScheduledConfMvpView extends MvpView {
    void displayEmptyList();

    void displayError(String message);

    void displayError(int message);

    void displayConferences(List<Conference> conferences);
}
