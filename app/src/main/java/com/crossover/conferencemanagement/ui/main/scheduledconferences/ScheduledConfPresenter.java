package com.crossover.conferencemanagement.ui.main.scheduledconferences;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.di.ActivityContext;
import com.crossover.conferencemanagement.di.ApplicationContext;
import com.crossover.conferencemanagement.ui.base.BasePresenter;
import com.crossover.conferencemanagement.util.Constant;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public class ScheduledConfPresenter extends BasePresenter<ScheduledConfMvpView> {

    private DataManager mDataManager;
    private CompositeDisposable mDisposable;
    private Context context;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadConferences();
        }
    };

    @Inject
    public ScheduledConfPresenter(DataManager dataManager, @ApplicationContext Context context) {
        this.mDataManager = dataManager;
        this.mDisposable = new CompositeDisposable();
        this.context = context;
    }

    @Override
    public void attachView(ScheduledConfMvpView mvpView) {
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
        mDataManager.loadConferencesForDoctor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}