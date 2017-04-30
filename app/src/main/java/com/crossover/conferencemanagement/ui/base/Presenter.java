package com.crossover.conferencemanagement.ui.base;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

/**
 * Every presenter in this class will either implement this class or extend BasePresenter class
 * indicating which MvpView it wants to get associated with
 *
 * @param <T>
 */
public interface Presenter<T extends MvpView> {
    void attachView(T mvpView);

    void detachView();
}
