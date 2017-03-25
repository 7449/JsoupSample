package com.fiction.detail.w;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fiction.R;
import com.fiction.detail.m.DetailModel;
import com.fiction.detail.p.DetailPresenter;
import com.fiction.detail.p.DetailPresenterImpl;
import com.fiction.detail.v.DetailView;
import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.zzhoujay.richtext.RichText;

/**
 * by y on 2017/1/8.
 */

public class DetailActivity extends BaseActivity implements DetailView, OnClickListener {
    private static final String URL = "url";

    private Toolbar toolbar;
    private ContentLoadingProgressBar progressBar;
    private TextView textView;
    private DetailPresenter presenter;
    private String onUrl = null;
    private String nextUrl = null;
    private NestedScrollView scrollView;

    public static void getInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(DetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        presenter = new DetailPresenterImpl(this);
        Bundle extras = getIntent().getExtras();
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        presenter.startDetail(extras.getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        progressBar = getView(R.id.progress_bar);
        textView = getView(R.id.detail_tv);
        scrollView = getView(R.id.scroll_view);
        getView(R.id.btn_next).setOnClickListener(this);
        getView(R.id.btn_on).setOnClickListener(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void netWorkSuccess(DetailModel data) {
        onUrl = data.onPage;
        nextUrl = data.nextPage;
        toolbar.setTitle(data.title);
        scrollView.scrollTo(0, 0);
        RichText.fromHtml(data.message).into(textView);
    }

    @Override
    public void netWorkError(Throwable e) {
        UIUtils.snackBar(getView(R.id.rootView), getString(R.string.network_error));
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (!TextUtils.isEmpty(nextUrl)) {
                    presenter.startDetail(nextUrl);
                }
                break;
            case R.id.btn_on:
                if (!TextUtils.isEmpty(onUrl)) {
                    presenter.startDetail(onUrl);
                }
                break;
        }
    }

}
