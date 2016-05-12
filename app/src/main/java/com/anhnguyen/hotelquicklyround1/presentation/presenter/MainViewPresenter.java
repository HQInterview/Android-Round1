/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.presentation.presenter;

import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.domain.interactor.LoadWebListUseCase;
import com.anhnguyen.hotelquicklyround1.presentation.ui.MainView;
import com.anhnguyen.hotelquicklyround1.utils.HLog;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewPresenter implements Presenter {

    private static final String TAG = "MainViewPresenter";

    @Inject
    LoadWebListUseCase loadWebListUseCase;

    Subscription subscription;
    private MainView mainView;

    @Inject
    public MainViewPresenter(){

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (subscription != null) subscription.unsubscribe();

    }

    public void doLoadWebList() {
        HLog.d(TAG, "doLoadWebList");
        mainView.showLoading();
        subscription = loadWebListUseCase.buildUseCaseObservable()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<Web>>() {
                @Override
                public void onCompleted() {
                    mainView.hideLoading();
                    HLog.d(TAG, "onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    mainView.hideLoading();
                    mainView.showError(e.getMessage());
                    HLog.d(TAG, "onError " + e.getMessage());
                    //showErrorMessage(new DefaultErrorBundle(e));
                }

                @Override
                public void onNext(List<Web> webs) {
                    if(mainView != null) mainView.renderContent(webs);
                }
            });
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}
