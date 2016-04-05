package com.hq.test1.utils;

/**
 * Created by nami on 4/5/16.
 */
public class DataUtil {

    public static String correctUrl(String originalUrl){
        return originalUrl.replace(Constants.USER_ID_KEY, Constants.USER_ID).
                replace(Constants.SECRET_ID_KEY, Constants.SECRET_ID).
                replace(Constants.CURRENCY_CODE_KEY, Constants.CURRENCY_CODE).
                replace(Constants.OFFER_ID_KEY, Constants.OFFER_ID).
                replace(Constants.SELECTED_VOUCHERS_KEY, Constants.SELECTED_VOUCHERS);
    }
}
