package com.crossover.conferencemanagement.ui.main.conferencelist;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class ConferenceListPresenter extends BasePresenter<ConferenceListMvpView> {

    private DataManager mDataManager;
    private CompositeDisposable mDisposable;

    @Inject
    public ConferenceListPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        this.mDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(ConferenceListMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.detachView();
    }

    public void loadConferences() {
        SingleObserver<List<Conference>> observer =
                new SingleObserver<List<Conference>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Conference> conferences) {
                        if (conferences.size() == 0) {
                            getMvpView().displayEmptyList();
                        } else {
                            getMvpView().displayConferences(conferences);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().displayError(e.getLocalizedMessage());
                    }
                };
        mDataManager.loadConferences()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
