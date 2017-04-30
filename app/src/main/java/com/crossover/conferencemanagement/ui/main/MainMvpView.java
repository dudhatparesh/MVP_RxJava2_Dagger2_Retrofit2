package com.crossover.conferencemanagement.ui.main;

import com.crossover.conferencemanagement.ui.base.MvpView;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public interface MainMvpView extends MvpView {
    void displayError(Throwable e);

    void loadDoctorHomeFragment();

    void loadAdminHomeFragment();

    void exit();
}
