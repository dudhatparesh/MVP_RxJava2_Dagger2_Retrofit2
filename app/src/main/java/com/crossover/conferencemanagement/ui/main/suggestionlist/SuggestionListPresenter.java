package com.crossover.conferencemanagement.ui.main.suggestionlist;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.data.model.Suggestion;
import com.crossover.conferencemanagement.ui.base.BasePresenter;
import com.crossover.conferencemanagement.util.Constant;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class SuggestionListPresenter extends BasePresenter<SuggestionListMvpView> {

    private DataManager mDataManager;
    private CompositeDisposable mDisposable;

    @Inject
    public SuggestionListPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        this.mDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(SuggestionListMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.detachView();
    }

    public void loadSuggestions() {
        SingleObserver<List<Suggestion>> observer =
                new SingleObserver<List<Suggestion>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Suggestion> conferences) {
                        if (conferences.size() == 0) {
                            getMvpView().displayEmptyList();
                        } else {
                            getMvpView().displaySuggestions(conferences);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().displayError(e.getLocalizedMessage());
                    }
                };
        mDataManager.getUserType()
                .flatMap(new Function<Integer, Single<List<Suggestion>>>() {
                    @Override
                    public Single<List<Suggestion>> apply(@NonNull Integer integer) throws Exception {
                        if (integer == Constant.UserType.Admin) {
                            return mDataManager.loadAdminSuggestions();
                        } else {
                            return mDataManager.loadDoctorSuggestions();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    public void getUserType() {
        SingleObserver<Integer> single = new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable.add(d);
            }

            @Override
            public void onSuccess(Integer integer) {
                if (integer == Constant.UserType.Doctor) {
                    getMvpView().displayFab();
                } else {
                    getMvpView().hideFab();
                }
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e.getLocalizedMessage());
            }
        };
        mDataManager.getUserType()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(single);
    }
}
