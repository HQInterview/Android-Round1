package com.hq.test1.controllers;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nami on 4/5/16.
 */
public class ApplicationClass extends Application {
    private static ApplicationClass mInstance;
    private RequestQueue mRequestQueue;
    private Context context;

    public ApplicationClass(Context context) {
        this.context = context;
    }

    public ApplicationClass() {
    }

    public static synchronized ApplicationClass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApplicationClass(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
            mRequestQueue.start();
        }
        return mRequestQueue;
    }
}