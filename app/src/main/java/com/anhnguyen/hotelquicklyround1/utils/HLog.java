/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1.utils;

import com.anhnguyen.hotelquicklyround1.AppConfig;

import android.util.Log;

public class HLog {
    public static void d(String tag, String msg){
        if(AppConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(AppConfig.DEBUG){
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if(AppConfig.DEBUG){
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(AppConfig.DEBUG){
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(AppConfig.DEBUG){
            Log.w(tag, msg);
        }
    }


    public static void d(String tag, String msg, Throwable tr){
        if(AppConfig.DEBUG){
            Log.d(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg, Throwable tr){
        if(AppConfig.DEBUG){
            Log.e(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg, Throwable tr){
        if(AppConfig.DEBUG){
            Log.i(tag, msg, tr);
        }
    }

    public static void v(String tag, String msg, Throwable tr){
        if(AppConfig.DEBUG){
            Log.v(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr){
        if(AppConfig.DEBUG){
            Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr){
        if(AppConfig.DEBUG){
            Log.w(tag, tr);
        }
    }
}
