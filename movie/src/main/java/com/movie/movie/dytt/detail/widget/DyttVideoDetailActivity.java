package com.movie.movie.dytt.detail.widget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.framework.base.BaseActivity;
import com.framework.utils.HtmlUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.movie.dytt.detail.model.DyttVideoDetailModel;
import com.movie.movie.dytt.detail.presenter.DyttVideoDetailPresenterImpl;
import com.movie.movie.dytt.detail.view.DyttVideoDetailView;

/**
 * by y on 2017/3/23
 * <p>
 * dytt电影界面
 */

public class DyttVideoDetailActivity extends BaseActivity implements
        DyttVideoDetailView {
    private static final String URL = "URL";

    private ContentLoadingProgressBar progressBar;
    private Toolbar toolbar;
    private WebView webView;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(DyttVideoDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initWebView();
        new DyttVideoDetailPresenterImpl(this).netWorkRequest(getIntent().getExtras().getString(URL));
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
        return R.layout.activity_dytt_video_detail;
    }


    @Override
    public void netWorkSuccess(DyttVideoDetailModel data) {
        toolbar.setTitle(data.title);
        webView.loadDataWithBaseURL(null, HtmlUtils.getHtml(data.message), HtmlUtils.getMimeType(), HtmlUtils.getCoding(), null);
    }


    @Override
    public void netWorkError() {
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

}
