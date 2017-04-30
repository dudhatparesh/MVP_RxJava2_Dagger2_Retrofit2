package com.crossover.conferencemanagement.ui.main.editsuggestion;

import com.crossover.conferencemanagement.data.DataManager;
import com.crossover.conferencemanagement.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class EditSuggestionPresenter extends BasePresenter<EditSuggestionMvpView> {
    private DataManager mDataManager;
    private CompositeDisposable disposable;

    @Inject
    public EditSuggestionPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
        disposable = new CompositeDisposable();
    }

    @Override
    public void attachView(EditSuggestionMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        super.detachView();
    }

    public void submitSuggestion(String topic, String detail) {

        CompletableObserver observer = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.dispose();
            }

            @Override
            public void onComplete() {
                getMvpView().suggestionSubmitted();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().displayError(e);
            }
        };
        mDataManager.submitSuggestion(topic, detail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

}
