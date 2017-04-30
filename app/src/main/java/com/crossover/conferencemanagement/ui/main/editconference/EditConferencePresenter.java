package com.crossover.conferencemanagement.ui.main.editconference;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Doctor;
import com.crossover.conferencemanagement.data.model.Invite;
import com.crossover.conferencemanagement.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class EditConferencePresenter extends BasePresenter<EditConferenceMvpView> {
    private DataManager mDataManager;
    private CompositeDisposable disposable;

    @Inject
    public EditConferencePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        disposable = new CompositeDisposable();
    }

    @Override
    public void attachView(EditConferenceMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.detachView();
    }

    public void addConference(String topic, String detail, String location, String date,
                              List<Invite> invites) {
        CompletableObserver observer = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                getMvpView().conferenceSaved();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().displayError(e);
            }
        };
        mDataManager.addConference(topic, detail, location, date, invites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    public void updateConference(String conferenceId, String topic, String detail, String location, String date,
                                 List<Invite> invites) {
        CompletableObserver observer = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                getMvpView().conferenceUpdated();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().displayError(e);
            }
        };
        mDataManager.updateConference(conferenceId
                , topic, detail, location, date,
                invites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    public void cancelConference(String conferenceId) {
        CompletableObserver observer = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                getMvpView().conferenceUpdated();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                getMvpView().displayError(e);
            }
        };
        mDataManager.cancelConference(conferenceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    public void loadDoctors() {
        SingleObserver<List<Doctor>> observer = new SingleObserver<List<Doctor>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onSuccess(List<Doctor> doctors) {
                getMvpView().displayDoctors(doctors);
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e);
            }
        };
        mDataManager.loadDoctors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}
