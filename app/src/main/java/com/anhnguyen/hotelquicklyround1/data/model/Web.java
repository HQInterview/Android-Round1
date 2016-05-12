/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.model;

import com.google.gson.annotations.SerializedName;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;

public class Web {

    @Column @PrimaryKey
    public String id;

    @Column
    @SerializedName("url")
    public String url;

    @Column
    @SerializedName("filePath")
    public String filePath;

    @Column
    @SerializedName("nameSpace")
    public String namespace;

    @Column
    @SerializedName("cache")
    public boolean cache;

    @Column
    @SerializedName("pageTitle")
    public String title;


    // TODO: convert json string
    @SerializedName("params")
    public String []params;


}
