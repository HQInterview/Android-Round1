package com.anhnguyen.hotelquicklyround1.presentation.ui.activity;

import com.anhnguyen.hotelquicklyround1.R;
import com.anhnguyen.hotelquicklyround1.data.model.Web;
import com.anhnguyen.hotelquicklyround1.presentation.presenter.MainViewPresenter;
import com.anhnguyen.hotelquicklyround1.presentation.ui.MainView;
import com.anhnguyen.hotelquicklyround1.presentation.ui.adapter.WebListAdapter;
import com.anhnguyen.hotelquicklyround1.utils.HLog;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.ScaleInTopAnimator;
import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

public class MainActivity extends BaseActivity implements MainView{

    private static final String TAG = "MainActivity";

    @Inject
    MainViewPresenter mainViewPresenter;

    @Bind(R.id.root_container)
    ViewGroup rootContainer;
    @Bind(R.id.rcv)
    public RecyclerView recyclerView;
    @Bind(R.id.progress_wheel)
    ContentLoadingProgressBar progressBar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        getApplicationComponent().inject(this);

        mainViewPresenter.setMainView(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setEnabled(false);
                mainViewPresenter.doLoadWebList();
            }
        });


        // setup when view ready
        final ViewTreeObserver obs = rootContainer.getViewTreeObserver();
        obs.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rootContainer.getViewTreeObserver().removeOnPreDrawListener(this);

                mainViewPresenter.doLoadWebList();

                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewPresenter.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainViewPresenter.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewPresenter.resume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void renderContent(List<Web> webs) {
        renderListWebs(webs);
    }

    private void renderListWebs(List<Web> webs) {
        HLog.d(TAG, "renderListWebs total " + webs.size() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setClipToPadding(false);

        WebListAdapter adapter = new WebListAdapter(this);
        adapter.setItems(webs);

        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter);
        recyclerView.setAdapter(slideInBottomAnimationAdapter);
        recyclerView.setItemAnimator(new ScaleInTopAnimator());
    }

    @Override
    public void showLoading() {
        progressBar.show();
    }

    @Override
    public void hideLoading() {
        // Delay a bit to indicate data load from web server
        if(progressBar != null) progressBar.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.hide();
                fab.setEnabled(true);
            }
        }, 300);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        showError(rootContainer, message);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
