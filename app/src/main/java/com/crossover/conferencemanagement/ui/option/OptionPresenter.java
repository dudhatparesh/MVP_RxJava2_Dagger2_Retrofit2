package com.crossover.conferencemanagement.ui.option;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.base.BasePresenter;
import com.crossover.conferencemanagement.ui.main.MainActivity;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

public class OptionPresenter extends BasePresenter<OptionMvpView> {

    private final DataManager mDataManager;
    private CompositeDisposable disposable;

    @Inject
    public OptionPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        this.disposable = new CompositeDisposable();
    }

    @Override
    public void attachView(OptionMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void checkUserLogin() {
        SingleObserver<Long> observer = new SingleObserver<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onSuccess(Long userId) {
                if (userId > 0)
                    getMvpView().openActivity(MainActivity.class, null);

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e);
            }


        };
        mDataManager.getUserIdCallable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer);
    }

}
