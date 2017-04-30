package com.crossover.conferencemanagement.ui.old;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {
    private final DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
