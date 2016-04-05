package com.hq.test1.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hq.test1.controllers.ApplicationClass;
import com.hq.test1.utils.Constants;
import com.hq.test1.controllers.DataController;
import com.hq.test1.utils.DataModel;
import com.hq.test1.adapters.MainAdapter;
import com.hq.test1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nami on 4/5/16.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView webViewListView;
    private MainAdapter mainAdapter;
    private boolean shouldCache = true;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.main_title));
        webViewListView = (ListView) findViewById(R.id.main_listview);
        mainAdapter = new MainAdapter();
        webViewListView.setAdapter(mainAdapter);
        webViewListView.setOnItemClickListener(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getData();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.FILE_PERMISSION_REQUEST_CODE);
        }
    }


    private void getData() {
        showLoadingDialog();
        ApplicationClass.getInstance(this).getRequestQueue().
                add(new JsonObjectRequest(Request.Method.GET,
                        Constants.DATA_URL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dismissLoadingDialog();
                                ArrayList<DataModel> data = new ArrayList<DataModel>();
                                try {
                                    data = new DataController().parseData(response, MainActivity.this, shouldCache);
                                    mainAdapter.setData(data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dismissLoadingDialog();
                            }
                        }));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.FILE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getData();
                } else {
                    shouldCache = false;
                    Toast.makeText(this, getString(R.string.cache_permission_denied), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DataModel dataModel = (DataModel) parent.getItemAtPosition(position);
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("dataModel", dataModel);
        startActivity(intent);
    }

    private void showLoadingDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setMessage(getString(R.string.loading_message));
        loadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}