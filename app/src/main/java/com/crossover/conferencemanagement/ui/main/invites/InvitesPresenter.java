package com.crossover.conferencemanagement.ui.main.invites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.di.ActivityContext;
import com.crossover.conferencemanagement.di.ApplicationContext;
import com.crossover.conferencemanagement.ui.base.BasePresenter;
import com.crossover.conferencemanagement.util.CommonUtils;
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

public class InvitesPresenter extends BasePresenter<InvitesMvpView> {
    //@Inject
    //ScheduledConfPresenter scheduledConfPresenter;
    private DataManager mDataManager;
    private CompositeDisposable mDisposable;
    //private LocalBroadcastManager manager;

    @Inject
    public InvitesPresenter(DataManager dataManager, LocalBroadcastManager manager) {
        this.mDataManager = dataManager;
        this.mDisposable = new CompositeDisposable();
        //this.manager = manager;
    }

    @Override
    public void attachView(InvitesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.detachView();
    }

    public void loadInvites() {
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
                            getMvpView().displayInvites(conferences);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().displayError(e.getLocalizedMessage());
                    }
                };
        mDataManager.loadInvites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    public void acceptInvite(final Conference conference) {
        SingleObserver<List<Conference>> observer = new SingleObserver<List<Conference>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onSuccess(List<Conference> conferences) {
                getMvpView().displayToast(R.string.invite_accepted);
                //manager.sendBroadcast(new Intent(Constant.Action.LOAD_SCHEDULE));
                //scheduledConfPresenter.loadConferences();
                if (conferences.size() == 0) {
                    getMvpView().displayEmptyList();
                } else {
                    getMvpView().displayInvites(conferences);
                }
            }


            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e.getLocalizedMessage());
            }
        };
        mDataManager.changeInviteStatus(conference, Constant.InviteStatus.ACCEPTED)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer);
    }

    public void rejectInvite(Conference conference) {
        SingleObserver<List<Conference>> observer = new SingleObserver<List<Conference>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onSuccess(List<Conference> conferences) {
                getMvpView().displayToast(R.string.invite_rejected);
                //scheduledConfPresenter.loadConferences();
                if (conferences.size() == 0) {
                    getMvpView().displayEmptyList();
                } else {
                    getMvpView().displayInvites(conferences);
                }
            }


            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e.getLocalizedMessage());
            }
        };
        mDataManager.changeInviteStatus(conference, Constant.InviteStatus.REJECTED)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer);
    }
}
