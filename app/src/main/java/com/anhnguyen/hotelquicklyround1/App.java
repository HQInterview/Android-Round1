/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1;

import com.anhnguyen.hotelquicklyround1.dependantInjection.ApplicationComponent;
import com.anhnguyen.hotelquicklyround1.dependantInjection.ApplicationModule;
import com.anhnguyen.hotelquicklyround1.dependantInjection.DaggerApplicationComponent;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import android.app.Application;

public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(this))
            .build();

        // init database
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
