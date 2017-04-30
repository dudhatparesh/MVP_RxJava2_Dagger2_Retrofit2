package com.crossover.conferencemanagement.ui.main.editconference;

import com.crossover.conferencemanagement.data.model.Doctor;
import com.crossover.conferencemanagement.ui.base.MvpView;

import java.util.List;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public interface EditConferenceMvpView extends MvpView {
    void conferenceSaved();

    void conferenceUpdated();
    void conferenceCancelled();
    void displayError(Throwable e);

    void displayDoctors(List<Doctor> doctors);
}
