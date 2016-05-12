/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.model;

import com.google.gson.annotations.SerializedName;

import com.anhnguyen.hotelquicklyround1.data.database.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Web extends BaseModel{

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
//    @SerializedName("params")
//    public String []params;

    @Override
    public String toString() {
        return "id " + id + " name: " + title + " url:" + url + " cache " + cache + " nameSpace " + namespace + " filePath " + filePath;
    }
}
