/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.dependantInjection;

import com.anhnguyen.hotelquicklyround1.App;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Provides;

public class ApplicationModule {

    private final App app;

    public ApplicationModule(App app){
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return this.app.getApplicationContext();
    }


}
