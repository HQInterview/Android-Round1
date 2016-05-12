/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.net.WebService;

import com.anhnguyen.hotelquicklyround1.data.model.Web;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GetWebListAPI {

    @Headers({
        "Content-Type: application/json;charset=UTF-8"
    })
    @GET("/en/1/android/index.json")
    Call<Map<String, Web>> getWebList();

}
