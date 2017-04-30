package com.crossover.conferencemanagement.ui.main.suggestionlist;

import com.crossover.conferencemanagement.data.model.Suggestion;
import com.crossover.conferencemanagement.ui.base.MvpView;

import java.util.List;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public interface SuggestionListMvpView extends MvpView {
    void displayEmptyList();

    void displayError(String message);

    void displayError(int message);

    void displaySuggestions(List<Suggestion> conferences);

    void displayFab();

    void hideFab();
}
