package com.anhnguyen.hotelquicklyround1.presentation.ui.activity;

import com.anhnguyen.hotelquicklyround1.R;
import com.anhnguyen.hotelquicklyround1.utils.HLog;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity{

    private static final String TAG = "MainActivity";

    @Bind(R.id.root_container)
    ViewGroup rootContainer;
    @Bind(R.id.webview)
    public WebView webView;
    @Bind(R.id.progress_wheel)
    ContentLoadingProgressBar progressBar;

    boolean isStarted;
    boolean isLoaded;

    String id;
    String title;
    String url;
    boolean cache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        ButterKnife.bind(this);
        getApplicationComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the WebView
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new MyWebClient());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("id");
            title = extras.getString("title");
            url = extras.getString("url");
            cache = extras.getBoolean("cache");
        }else{
            finish();
        }

        // setup when view ready
        final ViewTreeObserver obs = rootContainer.getViewTreeObserver();
        obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rootContainer.getViewTreeObserver().removeOnPreDrawListener(this);

                renderWeb(title, url, cache);

                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onDestroy() {
        webView.clearCache(true);

        super.onDestroy();
        // Clear the cache (this clears the WebViews cache for the entire application)
    }

    private void renderWeb(String name, String url, boolean shouldSave) {
        url = fillUrl(url);
        String path = getFilesDir() + File.separator +  "_" + id;
        HLog.d(TAG, "renderWeb url " + url + " cache " + cache );
        HLog.d(TAG, "renderWeb path " + path);


        if(cache && new File(path).exists()){ // try to load from cache first
            //webView.loadUrl("file://" + path);
            HLog.d(TAG, "renderWeb from cache " + path);
            String data = null;
            try {
                InputStream is = new FileInputStream(new File(path));
                data = convertStreamToString(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            webView.loadDataWithBaseURL(path, data, "text/html", "UTF-8", url);
//            try {
//
//                InputStream is = new FileInputStream(new File(path));
//                WebArchiveReader wr = new WebArchiveReader() {
//
//                    @Override
//                    public void onFinished(WebView v) {
//                        // we are notified here when the page is fully loaded.
//                        HLog.d(TAG, "Page from WebArchive fully loaded.");
//                        // If you need to set your own WebViewClient, do it here,
//                        // after the WebArchive was fully loaded:
//                        webView.setWebViewClient(new MyWebClient());
//                        // Any other code we need to execute after loading a page from a WebArchive...
//
//                    }
//                };
//                // To read from a file instead of an asset, use:
//                // FileInputStream is = new FileInputStream(fileName);
//                if (wr.readWebArchive(is)) {
//                    wr.loadToWebView(webView);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }else {
            progressBar.show();
            webView.loadUrl(url);
        }
    }

    private String fillUrl(String url) {
        url = url.replace("{userId}", "276");
        url = url.replace("{appSecretKey}", "gvx32RFZLNGhmzYrfDCkb9jypTPa8Q");
        url = url.replace("{currencyCode}", "USD");
        url = url.replace("{offerId}", "10736598");
        url = url.replace("{selectedVouchers}", "[]");
        return  url;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        // http://www.java2s.com/Code/Java/File-Input-Output/ConvertInputStreamtoString.htm
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        Boolean firstLine = true;
        while ((line = reader.readLine()) != null) {
            if(firstLine){
                sb.append(line);
                firstLine = false;
            } else {
                sb.append("\n").append(line);
            }
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url){
            HLog.d(TAG, "Web page loaded: " + url);
            super.onPageFinished(view, url);
            progressBar.hide();
            String path = getFilesDir() + File.separator +  "_" + id;
            if(cache && ! new File(path).exists()){
                HLog.d(TAG, "renderWeb save web archive " + path);
                webView.saveWebArchive(path);
            }
        }
    }

}
