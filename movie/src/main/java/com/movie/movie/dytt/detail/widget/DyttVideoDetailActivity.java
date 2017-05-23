package com.movie.movie.dytt.detail.widget;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.EasyWebView;
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

    private Toolbar toolbar;
    private EasyWebView webView;
    private ContentLoadingProgressBar progressBar;

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
        new DyttVideoDetailPresenterImpl(this).netWorkRequest(getIntent().getExtras().getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        webView = getView(R.id.webView);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dytt_video_detail;
    }


    @Override
    public void netWorkSuccess(DyttVideoDetailModel data) {
        toolbar.setTitle(data.title);
        webView.loadDataUrl(data.message);
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
