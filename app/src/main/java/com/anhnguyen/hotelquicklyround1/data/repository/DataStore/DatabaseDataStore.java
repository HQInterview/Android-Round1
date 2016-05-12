/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.repository.DataStore;

import com.anhnguyen.hotelquicklyround1.data.database.DatabaseAPI;
import com.anhnguyen.hotelquicklyround1.data.model.Web;

import android.content.Context;

import java.util.List;

import rx.Observable;

public class DatabaseDataStore implements WebDataStore {

    private Context context;
    private DatabaseAPI databaseAPI;

    public DatabaseDataStore(Context context, DatabaseAPI databaseAPI){
        this.context = context;
        this.databaseAPI = databaseAPI;
    }

    @Override
    public Observable<List<Web>> getWebList() {
        return null;
    }


}
