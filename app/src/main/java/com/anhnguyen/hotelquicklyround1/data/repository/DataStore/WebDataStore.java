/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.repository.DataStore;

import com.anhnguyen.hotelquicklyround1.data.model.Web;

import java.util.List;

import rx.Observable;

public interface WebDataStore {

    Observable<List<Web>> getWebList();

}