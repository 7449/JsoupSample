package com.movie.movie.piaohua.detail.widget;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.EasyWebView;
import com.movie.R;
import com.movie.movie.piaohua.detail.model.PiaoHuaDetailModel;
import com.movie.movie.piaohua.detail.presenter.PiaoHuaDetailPresenterImpl;
import com.movie.movie.piaohua.detail.view.PiaoHuaDetailView;

/**
 * by y on 2017/3/24.
 */

public class PiaoHuaDetailActivity extends BaseActivity implements PiaoHuaDetailView {

    private static final String URL = "url";
    private Toolbar toolbar;
    private EasyWebView webView;
    private ContentLoadingProgressBar progressBar;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(PiaoHuaDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        new PiaoHuaDetailPresenterImpl(this).netWorkRequest(getIntent().getExtras().getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        webView = getView(R.id.webView);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_piaohua_detail;
    }

    @Override
    public void netWorkSuccess(PiaoHuaDetailModel data) {
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
