/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.net.WebService;

import com.anhnguyen.hotelquicklyround1.data.model.Web;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Headers;

public interface GetWebListAPI {

    @Headers({
        "Content-Type: application/json;charset=UTF-8"
    })
    @GET("/en/1/android/index.json")
    List<Web> getWebList();

}
