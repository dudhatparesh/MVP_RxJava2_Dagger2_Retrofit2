package com.crossover.conferencemanagement.ui.option;

import android.os.Bundle;

import com.crossover.conferencemanagement.ui.base.MvpView;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

public interface OptionMvpView extends MvpView {
    void openActivity(Class activityClass,Bundle bundle);

    void displayError(Throwable e);
}
