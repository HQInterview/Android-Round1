/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.presentation.ui.activity;

import com.anhnguyen.hotelquicklyround1.App;
import com.anhnguyen.hotelquicklyround1.dependantInjection.ApplicationComponent;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.getApplicationComponent().inject(this);
        this.overridePendingTransition(0, 0); // remove activity transition animation
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this); // safe check unbind
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, 0); // remove activity transition animation
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    void showError(View view, String error) {
        Snackbar.make(view, error, Snackbar.LENGTH_SHORT).show();
    }

}
