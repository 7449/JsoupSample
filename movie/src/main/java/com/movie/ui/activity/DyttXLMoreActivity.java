package com.movie.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.adapter.DyttXLMoreAdapter;
import com.movie.manager.ApiConfig;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.DyttXLMorePresenterImpl;
import com.movie.mvp.presenter.PresenterManager;
import com.movie.mvp.view.ViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttXLMoreActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener, ViewManager.DyttXLMoreView {
    private static final String TITLE = "title";

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PresenterManager.DyttXLMorePresenter presenter;

    private DyttXLMoreAdapter adapter;

    public static void startIntent(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        UIUtils.startActivity(DyttXLMoreActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        presenter = new DyttXLMorePresenterImpl(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getIntent().getExtras().getString(TITLE));

        adapter = new DyttXLMoreAdapter(new ArrayList<>());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.post(this::onRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dytt_more_xl;
    }

    @Override
    public void onRefresh() {
        presenter.netWorkRequest(ApiConfig.DYTT_XL);
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
        adapter.removeAll();
        adapter.addAll(data);
    }

    @Override
    public void netWorkError() {
        UIUtils.snackBar(getView(R.id.coordinatorLayout), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

}
