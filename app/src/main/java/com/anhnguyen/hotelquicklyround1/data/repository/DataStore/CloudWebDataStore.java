/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.repository.DataStore;

import com.anhnguyen.hotelquicklyround1.data.database.DatabaseAPI;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.data.net.RestAPI;
import com.anhnguyen.hotelquicklyround1.utils.HLog;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

public class CloudWebDataStore implements WebDataStore{

    private static final String TAG = "CloudWebDataStore";

    private final RestAPI restApi;
    private final DatabaseAPI databaseApi;

    private final Action1<List<Web>> saveWebListToDB =
        entities -> {
            if (entities != null && entities.size() > 0) {
                HLog.d(TAG, "saveStoriesToDbAction entities.size " + entities.size());
                CloudWebDataStore.this.databaseApi.saveWebList(entities);
            }
        };

    public CloudWebDataStore(RestAPI restApi, DatabaseAPI databaseApi) {
        this.restApi = restApi;
        this.databaseApi = databaseApi;
    }

    @Override
    public Observable<List<Web>> getWebList() {
        return restApi.getWebList().doOnNext(saveWebListToDB);
    }

}
