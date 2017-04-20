package com.image.search.detail.widget;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.ExtendedViewPager;
import com.image.R;
import com.image.collection.sql.CollectionUtils;
import com.image.search.detail.model.SearchDetailModel;
import com.image.search.detail.presenter.SearchDetailPresenter;
import com.image.search.detail.presenter.SearchDetailPresenterImpl;
import com.image.search.detail.view.SearchDetailView;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2017/3/23
 */

public class SearchDetailActivity extends BaseActivity implements SearchDetailView {
    private static final String URL = "URL";
    private String url;
    private Toolbar toolbar;
    private ExtendedViewPager viewPager;
    private ContentLoadingProgressBar loadingProgressBar;
    private SearchDetailAdapter adapter;

    public static void startIntent(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(SearchDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(URL);
        }

        SearchDetailPresenter presenter = new SearchDetailPresenterImpl(this);
        adapter = new SearchDetailAdapter(new ArrayList<>());
        viewPager.setAdapter(adapter);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getString(R.string.dbmz_title));
        presenter.netWorkRequest(url);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                invalidateOptionsMenu();
            }
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.collection) {
                String imageUrl = adapter.getUrl(viewPager.getCurrentItem());
                if (TextUtils.isEmpty(imageUrl)) {
                    UIUtils.snackBar(getView(R.id.ll_layout), getString(R.string.collection_loading));
                } else {
                    if (CollectionUtils.isEmpty(imageUrl)) {
                        CollectionUtils.insert(imageUrl);
                        UIUtils.snackBar(getView(R.id.ll_layout), getString(R.string.collection_ok));
                    } else {
                        CollectionUtils.deleted(imageUrl);
                        UIUtils.snackBar(getView(R.id.ll_layout), getString(R.string.collection_deleted));
                    }
                    invalidateOptionsMenu();
                }
            }
            return true;
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
        return R.layout.activity_search_detail;
    }


    @Override
    public void netWorkSuccess(List<SearchDetailModel> data) {
        adapter.addAll(data);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_menu, menu);
        MenuItem item = menu.findItem(R.id.collection);
        String url = adapter.getUrl(viewPager.getCurrentItem());
        if (!TextUtils.isEmpty(url)) {
            item.setIcon(CollectionUtils.isEmpty(adapter.getUrl(viewPager.getCurrentItem())) ? R.drawable.ic_favorite_border_24dp : R.drawable.ic_favorite_24dp);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void netWorkError() {
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
