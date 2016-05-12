/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.database;

import com.anhnguyen.hotelquicklyround1.data.model.Web;

import android.content.Context;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class DatabaseApiImpl implements DatabaseAPI{

    private Context context;

    public DatabaseApiImpl(Context context){
        this.context = context;
    }

    @Override
    public Observable<List<Web>> getWebList() {
        return Observable.create(new Observable.OnSubscribe<List<Web>>() {
            @Override
            public void call(Subscriber<? super List<Web>> subscriber) {

            }
        });
    }

    @Override
    public Observable<List<Web>> saveWebList(List<Web> web) {
        return Observable.create(new Observable.OnSubscribe<List<Web>>() {
            @Override
            public void call(Subscriber<? super List<Web>> subscriber) {

            }
        });
    }

}
