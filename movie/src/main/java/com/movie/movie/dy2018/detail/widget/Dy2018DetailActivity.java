package com.movie.movie.dy2018.detail.widget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.framework.base.BaseActivity;
import com.framework.utils.HtmlUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.movie.dy2018.detail.model.Dy2018DetailModel;
import com.movie.movie.dy2018.detail.presenter.Dy2018DetailPresenterImpl;
import com.movie.movie.dy2018.detail.view.Dy2018DetailView;

/**
 * by y on 2017/3/24.
 */

public class Dy2018DetailActivity extends BaseActivity implements Dy2018DetailView {

    private static final String URL = "url";
    private ContentLoadingProgressBar progressBar;
    private Toolbar toolbar;
    private WebView webView;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(Dy2018DetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initWebView();
        new Dy2018DetailPresenterImpl(this).netWorkRequest(getIntent().getExtras().getString(URL));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(false);
    }

    @Override
    protected void initById() {
        progressBar = getView(R.id.progress_bar);
        toolbar = getView(R.id.toolbar);
        webView = getView(R.id.webView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dy2018_detail;
    }

    @Override
    public void netWorkSuccess(Dy2018DetailModel data) {
        toolbar.setTitle(data.title);
        webView.loadDataWithBaseURL(null, HtmlUtils.getHtml(data.message), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null);
    }

    @Override
    public void netWorkError(Throwable e) {
        UIUtils.snackBar(getView(R.id.coordinatorLayout), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        progressBar.show();
    }

    @Override
    public void hideProgress() {
        progressBar.hide();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
