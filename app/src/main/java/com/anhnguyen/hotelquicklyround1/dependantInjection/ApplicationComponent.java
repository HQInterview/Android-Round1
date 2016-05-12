/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.dependantInjection;

import com.anhnguyen.hotelquicklyround1.presentation.ui.activity.BaseActivity;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    Context context();

    void inject(BaseActivity baseActivity);

}
