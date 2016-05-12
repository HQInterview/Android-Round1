/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1;

import com.anhnguyen.hotelquicklyround1.dependantInjection.ApplicationComponent;

import android.app.Application;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
