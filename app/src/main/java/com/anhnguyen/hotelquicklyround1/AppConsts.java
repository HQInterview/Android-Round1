/**
 * Hotel Quickly Round 1
 *
 * Created by Anh Nguyen on 5/12/16.
 * Copyright (c) 2016 Anh Nguyen. All rights reserved.
 */
package com.anhnguyen.hotelquicklyround1;

import java.util.HashMap;

public class AppConsts {

    public static final String SRC_WEB_LIST = "http://appcontent.hotelquickly.com/en/1/android/index.json";

    public static final HashMap<String, String> sMapValues = new HashMap<>();

    static{
        sMapValues.put("userId", "276");
        sMapValues.put("appSecretKey", "gvx32RFZLNGhmzYrfDCkb9jypTPa8Q");
        sMapValues.put("currencyCode", "USD");
        sMapValues.put("offerId", "10736598");
        sMapValues.put("selectedVouchers", " []");
    }

}
