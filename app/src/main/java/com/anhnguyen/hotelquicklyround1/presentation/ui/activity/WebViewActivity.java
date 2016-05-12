package com.anhnguyen.hotelquicklyround1.presentation.ui.activity;

import com.anhnguyen.hotelquicklyround1.R;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.data.model.Web_Table;
import com.anhnguyen.hotelquicklyround1.utils.HLog;
import com.anhnguyen.hotelquicklyround1.utils.Utils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;

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
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

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

        setSupportActionBar(toolbar);

        // Initialize the WebView
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebViewClient(new MyWebClient());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renderWeb(title, url, cache);
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
        // Clear the cache (this clears the WebViews cache for the entire application)
        webView.clearCache(true);
        super.onDestroy();
    }

    private void renderWeb(String name, String url, boolean shouldSave) {
        url = Utils.fillUrl(url);
        String path = getFilesDir() + File.separator +  "_" + id;
        HLog.d(TAG, "renderWeb url " + url + " cache " + cache );
        HLog.d(TAG, "renderWeb path " + path);


        if(cache){ // try to load from cache first
            Web web = SQLite.select().from(Web.class).where(Web_Table.id.eq(id)).querySingle();
            if(web != null && ! TextUtils.isEmpty(web.cacheData)) {
                webView.loadData(web.cacheData, "text/html", "UTF-8");
                HLog.d(TAG, "renderWeb cache: " + web.cacheData);
                return; // no need process more
            }
        }

        // else
        progressBar.show();
        webView.loadUrl(url);
    }

    private class MyWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url){
            HLog.d(TAG, "Web page loaded: " + url);
            super.onPageFinished(view, url);

            // Delay a bit to indicate data load from web server
            if(progressBar != null) progressBar.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.hide();
                }
            }, 300);

        }
    }


}
