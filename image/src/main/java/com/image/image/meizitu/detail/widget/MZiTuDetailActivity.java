package com.image.image.meizitu.detail.widget;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.image.meizitu.detail.model.MZiTuDetailModel;
import com.image.image.meizitu.detail.presenter.MZiTuDetailPresenter;
import com.image.image.meizitu.detail.presenter.MZiTuDetailPresenterImpl;
import com.image.image.meizitu.detail.view.MZiTuDetailView;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2017/3/23
 */

public class MZiTuDetailActivity extends BaseActivity implements MZiTuDetailView {
    private static final String URL = "URL";
    private String url;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ContentLoadingProgressBar loadingProgressBar;
    private MZiTuDetailPresenter presenter;
    private MZiTuDetailAdapter adapter;
    private int page = 1;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(MZiTuDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(URL);
        }

        presenter = new MZiTuDetailPresenterImpl(this);
        adapter = new MZiTuDetailAdapter(new ArrayList<>());
        viewPager.setAdapter(adapter);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getString(R.string.mzitu_title));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == adapter.getCount() - 1) {
                    ++page;
                    presenter.netWorkRequest(url, page);
                }
            }
        });
        presenter.netWorkRequest(url, page);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        viewPager = getView(R.id.viewPager);
        loadingProgressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mzitu_detail;
    }

    @Override
    public void netWorkSuccess(List<MZiTuDetailModel> data) {
        adapter.addAll(data);
        if (page == 1 && adapter.getCount() == 1) {
            ++page;
            presenter.netWorkRequest(url, page);
        }
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
