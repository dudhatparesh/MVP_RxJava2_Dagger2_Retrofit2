package com.crossover.conferencemanagement.ui.authentication.login;

import com.crossover.conferencemanagement.ui.base.MvpView;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public interface LoginMvpView extends MvpView {
    void displayToast(String message);

    void displayToast(int messageResourceId);

    void openActivity(Class activityClass);
}
