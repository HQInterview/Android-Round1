/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.database;

import com.anhnguyen.hotelquicklyround1.AppConfig;
import com.anhnguyen.hotelquicklyround1.data.exception.DBException;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.data.model.Web_Table;
import com.anhnguyen.hotelquicklyround1.utils.HLog;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.Subscriber;

public class DatabaseApiImpl implements DatabaseAPI{

    private static final String TAG = "DatabaseApiImpl";
    private static final long STALE_MS = 5 * 1000; // Data is stale after 5 seconds

    private Context context;
    long timestamp = System.currentTimeMillis();

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
                    List<Web> webs = new ArrayList<>();
                    if(isUpToDate()){ // after a delta time we should refresh data from server
                        webs = loadWebListFromDB();
                    }

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
        timestamp = System.currentTimeMillis();
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

                    if(! TextUtils.isEmpty(web.title)) {
                        Web old = SQLite.select().from(Web.class).where(Web_Table.title.eq(web.title)).querySingle();
                        if (old != null) {
                            web.id = old.id;
                            if(! old.url.equals(web.url)){ // reset data
                                old.cacheData = null;
                            }
                            old.url = web.url;
                            old.cache = web.cache;
                            old.filePath = web.filePath;
                            old.namespace = web.namespace;
                            old.pageTitle = web.pageTitle;
                            old.update(databaseWrapper);
                            HLog.d(TAG, "saveWebListToDb UPDATE " + web);
                            continue; // no need more process
                        }
                    }

                    HLog.d(TAG, "saveWebListToDb " + web);
                    // else
                    web.save(databaseWrapper);
                }

                if(AppConfig.DEBUG) {
                    List<Web> webs1 = SQLite.select().from(Web.class).queryList();
                    HLog.d(TAG, "saveWebListToDb total in db " + webs1.size());
                }
            }
        });

    }

    public List<Web> loadWebListFromDB() {
        timestamp = System.currentTimeMillis();
        return SQLite.select().from(Web.class).queryList();
    }

    public boolean isUpToDate() {
        return System.currentTimeMillis() - timestamp < STALE_MS;
    }

}
