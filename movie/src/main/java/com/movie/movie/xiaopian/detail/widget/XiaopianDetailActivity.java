package com.movie.movie.xiaopian.detail.widget;

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
import com.movie.movie.xiaopian.detail.model.XiaoPianDetailModel;
import com.movie.movie.xiaopian.detail.presenter.XiaoPianDetailPresenterImpl;
import com.movie.movie.xiaopian.detail.view.XiaoPianDetailView;

/**
 * by y on 2017/3/24.
 */

public class XiaopianDetailActivity extends BaseActivity implements XiaoPianDetailView {

    private static final String URL = "url";
    private ContentLoadingProgressBar progressBar;
    private Toolbar toolbar;
    private WebView webView;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(XiaopianDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initWebView();
        new XiaoPianDetailPresenterImpl(this).netWorkRequest(getIntent().getExtras().getString(URL));
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
        return R.layout.activity_xiaopian_detail;
    }

    @Override
    public void netWorkSuccess(XiaoPianDetailModel data) {
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


}
