package com.crossover.conferencemanagement.ui.authentication.registration;

import android.text.TextUtils;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.base.BasePresenter;
import com.crossover.conferencemanagement.util.Constant;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public class RegistrationPresenter extends BasePresenter<RegistrationMvpView> {
    private final DataManager mDataManager;
    private CompositeDisposable compositeDisposable;

    @Inject
    public RegistrationPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(RegistrationMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void register(String username,
                         String password,
                         String experience,
                         String expertise,
                         String name,
                         final int userType) {
        CompletableObserver registerObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                getMvpView().displayToast(R.string.registered_successfully);
                getMvpView().openActivity(MainActivity.class);

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayToast(e.getLocalizedMessage());
            }
        };
        mDataManager.register(username, password, experience,
                expertise, name, userType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(registerObserver);
    }

    boolean validateData(String username,
                         String password,
                         String expertise,
                         String experience,
                         final int userType) {
        if (TextUtils.isEmpty(username)) {
            getMvpView().displayToast(R.string.username_can_not_be_empty);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            getMvpView().displayToast(R.string.password_can_not_be_empty);
            return false;
        }
        if (userType == Constant.UserType.Doctor) {
            if (TextUtils.isEmpty(expertise)) {
                getMvpView().displayToast(R.string.expertise_can_not_be_empty);
                return false;
            }
            if (TextUtils.isEmpty(experience)) {
                getMvpView().displayToast(R.string.experience_can_not_be_empty);
                return false;
            }

            if (!TextUtils.isDigitsOnly(experience)) {
                getMvpView().displayToast(R.string.experience_must_be_digit_only);
                return false;
            }
        }
        return true;
    }
}
