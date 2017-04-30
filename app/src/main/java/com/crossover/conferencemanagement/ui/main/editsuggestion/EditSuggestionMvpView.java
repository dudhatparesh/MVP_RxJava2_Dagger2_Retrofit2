package com.crossover.conferencemanagement.ui.main.editsuggestion;

import com.crossover.conferencemanagement.ui.base.MvpView;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public interface EditSuggestionMvpView extends MvpView {
    void suggestionSubmitted();

    void displayError(Throwable e);

}
