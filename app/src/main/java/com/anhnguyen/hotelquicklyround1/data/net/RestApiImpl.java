/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.anhnguyen.hotelquicklyround1.data.exception.NetworkConnectionException;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.data.net.WebService.GetWebListAPI;
import com.anhnguyen.hotelquicklyround1.utils.HLog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.Subscriber;

public class RestApiImpl implements RestAPI {

    private final Context context;

    /**
     * Constructor of the class
     *
     * @param context {@link Context}.
     */
    public RestApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }


    @Override
    public Observable<List<Web>> getWebList() {
        return Observable.create(new Observable.OnSubscribe<List<Web>>() {
            @Override
            public void call(Subscriber<? super List<Web>> subscriber) {
                if (isThereInternetConnection()) {
                    try {
                        HLog.d("getWebList", "Begin getWebList");
                        List<Web> songEntities = getWebListFromAPI();
                        if (songEntities != null) {
                            HLog.d("getWebList", "getWebList " + songEntities.size());
                            subscriber.onNext(songEntities);
                            subscriber.onCompleted();
                        } else {
                            HLog.d("getWebList", "getWebList failed");
                            subscriber.onError(new NetworkConnectionException("Cannot getFeeds through cloud-Api"));
                        }
                    } catch (Exception e) {
                        HLog.d("getWebList", "getWebList error");
                        e.printStackTrace();
                        subscriber.onError(new NetworkConnectionException("getWebList error " + e.getCause()));
                    }
                } else {
                    subscriber.onError(new NetworkConnectionException("Don't have internet access"));
                }
            }
        });
    }

    private List<Web> getWebListFromAPI() throws MalformedURLException {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

        final GetWebListAPI getWebListAPI =
            new RestAdapter.Builder()
                .setEndpoint(RestAPI.API_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        HLog.d("Retrofit", message);
                    }
                }).build().create(GetWebListAPI.class);
        return getWebListAPI.getWebList();
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
            (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
