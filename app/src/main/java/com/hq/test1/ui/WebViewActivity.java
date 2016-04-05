package com.hq.test1.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.hq.test1.R;
import com.hq.test1.utils.Constants;
import com.hq.test1.utils.DataModel;
import com.hq.test1.utils.FileUtil;

/**
 * Created by nami on 4/5/16.
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog loadingDialog;
    private FileUtil fileUtils;
    private DataModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.webview_toolbar);
        setSupportActionBar(toolbar);
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new TestWebViewClient());
        fileUtils = new FileUtil();

        Intent intent = getIntent();
        dataModel = (DataModel) intent.getSerializableExtra("dataModel");
        getSupportActionBar().setTitle(dataModel.getTitle());
        if (fileUtils.checkFile(dataModel.getTitle() + Constants.HTML_SUFFIX)) {
            webView.loadUrl("file:///" + fileUtils.getFilePath(dataModel.getTitle() + Constants.HTML_SUFFIX));
        } else {
            loadHTML(dataModel.getUrl());
        }
    }

    private void loadHTML(String url) {
        showLoadingDialog();
        webView.loadUrl(url);
    }

    private void showLoadingDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setCancelable(true);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setMessage(getString(R.string.loading_message));
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(WebViewActivity.this,
                        getString(R.string.html_load_failure),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        loadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    private class TestWebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // This is where the link click within the view is handled. (2nd question on the github page)
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            dismissLoadingDialog();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            dismissLoadingDialog();
            if (!fileUtils.checkFile(dataModel.getTitle() + Constants.HTML_SUFFIX))
                Toast.makeText(WebViewActivity.this,
                        getString(R.string.html_load_failure),
                        Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.setWebViewClient(null);
    }
}