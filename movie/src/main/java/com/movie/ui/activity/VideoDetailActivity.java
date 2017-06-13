package com.movie.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.EasyWebView;
import com.movie.R;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.VideoDetailPresenterImpl;
import com.movie.mvp.view.ViewManager;

/**
 * by y on 2017/6/6.
 */

public class VideoDetailActivity extends BaseActivity<VideoDetailPresenterImpl> implements ViewManager.Dy2018DetailView {

    private static final String URL = "url";
    private Toolbar toolbar;
    private EasyWebView webView;
    private ContentLoadingProgressBar progressBar;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(VideoDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        mPresenter.netWorkRequest(getIntent().getExtras().getString(URL));
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        mPresenter.netWorkRequest(getIntent().getExtras().getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        webView = getView(R.id.webView);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected VideoDetailPresenterImpl initPresenterImpl() {
        return new VideoDetailPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_detail;
    }

    @Override
    public void netWorkSuccess(MovieModel data) {
        if (mStatusView != null) {
            toolbar.setTitle(data.title);
            webView.loadDataUrl(data.message);
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            UIUtils.snackBar(mStatusView, getString(R.string.network_error));
        }
    }

    @Override
    public void showProgress() {
        if (progressBar != null)
            progressBar.show();
    }

    @Override
    public void hideProgress() {
        if (progressBar != null)
            progressBar.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}

