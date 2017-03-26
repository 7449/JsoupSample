package com.image.image.meizitu.detail.widget;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.ExtendedViewPager;
import com.image.R;
import com.image.image.meizitu.detail.model.MeiZiTuDetailModel;
import com.image.image.meizitu.detail.presenter.MeiZiTuDetailPresenterImpl;
import com.image.image.meizitu.detail.view.MeiZiTuDetailView;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2017/3/23
 */

public class MeiZiTuDetailActivity extends BaseActivity implements MeiZiTuDetailView {
    private static final String URL = "URL";
    private String url;
    private Toolbar toolbar;
    private ExtendedViewPager viewPager;
    private ContentLoadingProgressBar loadingProgressBar;
    private MeiZiTuDetailAdapter adapter;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(MeiZiTuDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(URL);
        }
        adapter = new MeiZiTuDetailAdapter(new ArrayList<>());
        viewPager.setAdapter(adapter);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitles(1, adapter.getCount());
        new MeiZiTuDetailPresenterImpl(this).netWorkRequest(url);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setTitles(position + 1, adapter.getCount());
            }
        });
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        viewPager = getView(R.id.viewPager);
        loadingProgressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meizitu_detail;
    }

    @Override
    public void netWorkSuccess(List<MeiZiTuDetailModel> data) {
        adapter.addAll(data);
        setTitles(1, adapter.getCount());
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

    private void setTitles(int page, int imageSize) {
        toolbar.setTitle(getString(R.string.mzitu_title) + "(" + page + "/" + imageSize + ")");
    }
}
