/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/13/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.utils;

import com.anhnguyen.hotelquicklyround1.data.database.AppDatabase;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

    public static String fillUrl(String url) {
        url = url.replace("{userId}", "276");
        url = url.replace("{appSecretKey}", "gvx32RFZLNGhmzYrfDCkb9jypTPa8Q");
        url = url.replace("{currencyCode}", "USD");
        url = url.replace("{offerId}", "10736598");
        url = url.replace("{selectedVouchers}", "[]");
        return  url;
    }


    public static void downloadAndSaveWebPage(String downloadLink, int choice, final Web web){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url(downloadLink)
            .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("downloadAndSaveWebPage", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String aFinalString = response.body().string();
                    FlowManager.getDatabase(AppDatabase.class).executeTransaction(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {
                            web.cacheData = aFinalString;
                            web.save();
                        }
                    });

                } else {
                    HLog.e("downloadAndSaveWebPage", "Error");
                }
            }
        });
    }


    public static String convertStreamToString(InputStream is) throws Exception {
        // http://www.java2s.com/Code/Java/File-Input-Output/ConvertInputStreamtoString.htm
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        Boolean firstLine = true;
        while ((line = reader.readLine()) != null) {
            if(firstLine){
                sb.append(line);
                firstLine = false;
            } else {
                sb.append("\n").append(line);
            }
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }


}
