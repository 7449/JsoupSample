package com.image.image.detail.widget;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.image.detail.model.ImageDetailModel;
import com.image.image.detail.presenter.ImageDetailPresenter;
import com.image.image.detail.presenter.ImageDetailPresenterImpl;
import com.image.image.detail.view.ImageDetailView;
import com.image.manager.ApiConfig;
import com.image.manager.CorrectUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2017/3/23
 */

public class ImageDetailActivity extends BaseActivity implements ImageDetailView {
    private static final String URL = "URL";
    private static final String TYPE = "TYPE";
    private String url;
    private String type;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ContentLoadingProgressBar loadingProgressBar;
    private ImageDetailPresenter presenter;
    private ImageDetailAdapter adapter;
    private int page = 1;

    public static void startIntent(String url, String type) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TYPE, type);
        UIUtils.startActivity(ImageDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(URL);
            type = extras.getString(TYPE);
        }

        presenter = new ImageDetailPresenterImpl(this);
        adapter = new ImageDetailAdapter(new ArrayList<>());
        viewPager.setAdapter(adapter);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getString(CorrectUtils.getDetailTitle(type)));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (type) {
                    case ApiConfig.Type.M_ZI_TU_DETAIL:
                        if (position == adapter.getCount() - 1) {
                            ++page;
                            presenter.netWorkRequest(url, page, type);
                        }
                        break;
                }
            }
        });
        presenter.netWorkRequest(url, page, type);
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
        return R.layout.activity_image_detail;
    }

    @Override
    public void netWorkSuccess(List<ImageDetailModel> data) {
        adapter.addAll(data);
        if (page == 1 && adapter.getCount() == 1) {
            ++page;
            presenter.netWorkRequest(url, page, type);
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

    @Override
    public String getType() {
        return type;
    }
}
