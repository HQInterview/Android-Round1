package com.hq.test1.controllers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hq.test1.utils.Constants;
import com.hq.test1.utils.FileUtil;

/**
 * Created by nami on 4/5/16.
 */
public class CacheController {
    private Context context;

    public CacheController(Context context) {
        this.context = context;
    }

    public void getHTMLCache(String url, String name) {
        /*
        Another approach here would be creating a Webview here, loading the url on it and use the native WebView
        cache to load the cached == true pages.
        */
        final String htmlName = name + Constants.HTML_SUFFIX;
        ApplicationClass.getInstance(context).getRequestQueue().
                add(new StringRequest(Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                new FileUtil().saveHTML(response, htmlName);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
    }
}