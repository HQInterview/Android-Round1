/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.database;

import com.anhnguyen.hotelquicklyround1.data.exception.DBException;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.utils.HLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;

public class DatabaseApiImpl implements DatabaseAPI{

    private static final String TAG = "DatabaseApiImpl";

    private Context context;

    public DatabaseApiImpl(Context context){
        this.context = context;
    }

    @Override
    public Observable<List<Web>> getWebList() {
        return Observable.create(new Observable.OnSubscribe<List<Web>>() {
            @Override
            public void call(Subscriber<? super List<Web>> subscriber) {
                try {
                    Log.d(TAG, "getWebList");
                    List<Web> webs = loadWebListFromDB();
                    Log.d(TAG, "getWebList size " + webs.size());
                    subscriber.onNext(webs);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(new DBException(e.getCause()));
                }
            }
        });
    }

    @Override
    public boolean saveWebList(final List<Web> webs) {
        Log.d(TAG, "saveWebList to DB");
        saveWebListToDb(webs);
        return true;
    }


    public void saveWebListToDb(final List<Web> webs) {
        HLog.d(TAG, "saveWebListToDb webs.size " + webs.size());
        FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (Web web : webs) {
                    if(TextUtils.isEmpty(web.id)) web.id = UUID.randomUUID().toString();
                    HLog.d(TAG, "saveWebListToDb " + web);
                    web.save(databaseWrapper);
                }
            }
        });

    }

    public List<Web> loadWebListFromDB() {
        return SQLite.select().from(Web.class).queryList();
    }

}
