package com.image.image.douban.detail.widget;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.image.douban.detail.model.DouBanDetailModel;
import com.image.image.douban.detail.presenter.DouBanDetailPresenter;
import com.image.image.douban.detail.presenter.DouBanDetailPresenterImpl;
import com.image.image.douban.detail.view.DouBanDetailView;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2017/3/23
 */

public class DouBanDetailActivity extends BaseActivity implements DouBanDetailView {
    private static final String URL = "URL";
    private String url;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ContentLoadingProgressBar loadingProgressBar;
    private DouBanDetailAdapter adapter;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(DouBanDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(URL);
        }

        DouBanDetailPresenter presenter = new DouBanDetailPresenterImpl(this);
        adapter = new DouBanDetailAdapter(new ArrayList<>());
        viewPager.setAdapter(adapter);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getString(R.string.dbmz_title));
        presenter.netWorkRequest(url);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        viewPager = getView(R.id.viewPager);
        loadingProgressBar = getView(R.id.progress_bar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_douban_detail;
    }


    @Override
    public void netWorkSuccess(List<DouBanDetailModel> data) {
        adapter.addAll(data);
    }

    @Override
    public void netWorkError(Throwable e) {
        UIUtils.snackBar(getView(R.id.ll_layout), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        loadingProgressBar.show();
    }

    @Override
    public void hideProgress() {
        loadingProgressBar.hide();
    }

}
