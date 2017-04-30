package com.crossover.conferencemanagement.ui.base;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

/**
 * Base class that implements Presenter interface and implements attach and detach view.
 * It also keeps reference of MvpView so child class can access it through getMvpView method.
 *
 * @param <T>
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {
    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }
}
