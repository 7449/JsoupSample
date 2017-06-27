package com.fiction.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.FictionDetailPresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.EasyWebView;
import com.framework.widget.StatusLayout;

/**
 * by y on 2017/4/6.
 */

public class FictionDetailActivity extends BaseActivity<FictionDetailPresenterImpl>
        implements
        ViewManager.FictionDetailView, View.OnClickListener,
        EasyWebView.WebViewLoadListener {
    private static final String URL = "url";
    private static final String TYPE = "type";

    private String type = ApiConfig.Type.ZW_81;

    private Toolbar toolbar;
    private ContentLoadingProgressBar progressBar;
    private String onUrl = null;
    private String nextUrl = null;
    private String tempUrl = null;
    private EasyWebView webView;
    private FrameLayout frameLayout;

    public static void getInstance(String type, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TYPE, type);
        UIUtils.startActivity(FictionDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        webView.setLoadListener(this);
        Bundle extras = getIntent().getExtras();
        type = extras.getString(TYPE);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        startNetWork(extras.getString(URL), type);
    }

    @Override
    protected void initById() {
        webView = getView(R.id.detail_webView);
        frameLayout = getView(R.id.btn_rootView);
        toolbar = getView(R.id.toolbar);
        progressBar = getView(R.id.progress_bar);
        getView(R.id.btn_next).setOnClickListener(this);
        getView(R.id.btn_on).setOnClickListener(this);
    }

    @Override
    protected FictionDetailPresenterImpl initPresenterImpl() {
        return new FictionDetailPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fiction_list_detail;
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        toolbar.setTitle("network error");
        startNetWork(tempUrl, type);
    }

    @Override
    public void netWorkSuccess(FictionModel data) {
        if (mStatusView != null) {
            webView.setVisibility(View.VISIBLE);
            onUrl = data.onPage;
            nextUrl = data.nextPage;
            toolbar.setTitle(data.title);
            webView.loadDataUrl(data.message);
            webView.post(() -> webView.scrollTo(0, 0));
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            setStatusViewStatus(StatusLayout.ERROR);
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress() {
        if (mStatusView != null) {
            frameLayout.setVisibility(View.GONE);
            progressBar.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mStatusView != null)
            progressBar.hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (!TextUtils.isEmpty(nextUrl)) {
                    startNetWork(nextUrl, type);
                } else {
                    UIUtils.toast(UIUtils.getString(R.string.data_empty));
                }
                break;
            case R.id.btn_on:
                if (!TextUtils.isEmpty(onUrl)) {
                    startNetWork(onUrl, type);
                } else {
                    UIUtils.toast(UIUtils.getString(R.string.data_empty));
                }
                break;
        }
    }


    @Override
    public void loadingSuccess() {
        frameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }

    private void startNetWork(String string, String type) {
        setStatusViewStatus(StatusLayout.SUCCESS);
        tempUrl = string;
        mPresenter.startDetail(string, type);
    }
}
