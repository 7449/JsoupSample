package com.movie.movie.xiaopian.detail.widget;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.EasyWebView;
import com.movie.R;
import com.movie.movie.xiaopian.detail.model.XiaoPianDetailModel;
import com.movie.movie.xiaopian.detail.presenter.XiaoPianDetailPresenterImpl;
import com.movie.movie.xiaopian.detail.view.XiaoPianDetailView;

/**
 * by y on 2017/3/24.
 */

public class XiaopianDetailActivity extends BaseActivity implements XiaoPianDetailView {

    private static final String URL = "url";
    private Toolbar toolbar;
    private EasyWebView webView;
    private ContentLoadingProgressBar progressBar;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.INSTANCE.startActivity(XiaopianDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        new XiaoPianDetailPresenterImpl(this).netWorkRequest(getIntent().getExtras().getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        webView = getView(R.id.webView);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xiaopian_detail;
    }

    @Override
    public void netWorkSuccess(XiaoPianDetailModel data) {
        toolbar.setTitle(data.getTitle());
        webView.loadDataUrl(data.getMessage());
    }

    @Override
    public void netWorkError() {
        UIUtils.INSTANCE.snackBar(getView(R.id.coordinatorLayout), getString(R.string.network_error));
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
