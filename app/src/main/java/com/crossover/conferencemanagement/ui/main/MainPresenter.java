package com.crossover.conferencemanagement.ui.main;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.base.BasePresenter;
import com.crossover.conferencemanagement.util.Constant;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {
    private final DataManager mDataManager;
    private CompositeDisposable disposable;

    @Inject
    MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        this.disposable = new CompositeDisposable();
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void logout() {
        CompletableObserver observer = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onComplete() {
                getMvpView().exit();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e);
            }
        };
        mDataManager.logout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer);
    }

    public void checkLoggedinInUserType() {
        SingleObserver<Integer> observer = new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onSuccess(Integer integer) {
                switch (integer) {
                    case Constant.UserType.Doctor:
                        getMvpView().loadDoctorHomeFragment();
                        break;
                    case Constant.UserType.Admin:
                        getMvpView().loadAdminHomeFragment();
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e);
            }


        };
        mDataManager.getUserType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer);
    }
}
