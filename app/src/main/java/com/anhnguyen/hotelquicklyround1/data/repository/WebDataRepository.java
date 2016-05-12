/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.repository;

import com.anhnguyen.hotelquicklyround1.data.database.DatabaseAPI;
import com.anhnguyen.hotelquicklyround1.data.database.DatabaseApiImpl;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.data.net.RestAPI;
import com.anhnguyen.hotelquicklyround1.data.net.RestApiImpl;
import com.anhnguyen.hotelquicklyround1.data.repository.DataStore.CloudWebDataStore;
import com.anhnguyen.hotelquicklyround1.data.repository.DataStore.DatabaseDataStore;
import com.anhnguyen.hotelquicklyround1.domain.repository.WebRepository;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class WebDataRepository implements WebRepository {

    private Context context;
    private CloudWebDataStore cloudDataStore;
    private DatabaseDataStore databaseDataStore;

    @Inject
    public WebDataRepository(Context context){
        this.context = context;
        RestAPI restApi = new RestApiImpl(this.context);
        DatabaseAPI databaseApi = new DatabaseApiImpl(this.context);
        cloudDataStore = new CloudWebDataStore(restApi, databaseApi);
        databaseDataStore = new DatabaseDataStore(context, databaseApi);
    }

    @Override
    public Observable<List<Web>> getWebList() {
        final Observable<List<Web>> diskObservable = databaseDataStore.getWebList();
        final Observable<List<Web>> cloudObservable = cloudDataStore.getWebList();

        // Take source from database if has else get from cloud
        return Observable.concat(diskObservable, cloudObservable).first(webs -> webs != null && webs.size() > 0);
    }

}
