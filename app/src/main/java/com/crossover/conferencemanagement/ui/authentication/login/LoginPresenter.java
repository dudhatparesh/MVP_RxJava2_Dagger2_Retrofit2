package com.crossover.conferencemanagement.ui.authentication.login;

import android.text.TextUtils;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {
    private final DataManager mDataManager;
    private CompositeDisposable compositeDisposable;

    @Inject
    public LoginPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void login(String username, String password, final int userType) {
        SingleObserver<Boolean> loginObserver = new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    getMvpView().displayToast(R.string.logged_in_successfully);
                    getMvpView().openActivity(MainActivity.class);

                } else {
                    getMvpView().displayToast(R.string.login_failed);
                }
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayToast(e.getLocalizedMessage());
            }
        };
        mDataManager.login(username, password, userType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(loginObserver);
    }

    boolean validateData(String username,
                         String password) {
        if (TextUtils.isEmpty(username)) {
            getMvpView().displayToast(R.string.username_can_not_be_empty);
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            getMvpView().displayToast(R.string.password_can_not_be_empty);
            return false;
        }
        return true;
    }
}
