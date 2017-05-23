package com.movie.movie.dytt.more.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.framework.base.BaseActivity;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.movie.dytt.detail.widget.DyttVideoDetailActivity;
import com.movie.movie.dytt.more.model.DyttVideoMoreModel;
import com.movie.movie.dytt.more.presenter.DyttVideoMorePresenter;
import com.movie.movie.dytt.more.presenter.DyttVideoMorePresenterImpl;
import com.movie.movie.dytt.more.view.DyttVideoMoreView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttVideoMoreActivity extends BaseActivity
        implements DyttVideoMoreView,
        BaseRecyclerAdapter.OnItemClickListener<DyttVideoMoreModel>,
        SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {
    private static final String TYPE = "type";
    private static final String TITLE = "title";
    private static final String PLACE = "place";
    private int page = 1;
    private int placeType = ApiConfig.Type.DYTT_CHOSEN_TYPE;
    private int type = DyttJsoupManager.TYPE_2016;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private DyttVideoMorePresenter presenter;
    private DyttVideoMoreAdapter adapter;

    public static void startIntent(int type, int placeType, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bundle.putInt(PLACE, placeType);
        bundle.putString(TITLE, title);
        UIUtils.startActivity(DyttVideoMoreActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Bundle extras = getIntent().getExtras();
        placeType = extras.getInt(PLACE);
        type = extras.getInt(TYPE);
        toolbar.setTitle(extras.getString(TITLE));
        adapter = new DyttVideoMoreAdapter(new ArrayList<>());
        adapter.setOnItemClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(adapter);

        presenter = new DyttVideoMorePresenterImpl(this);

        swipeRefreshLayout.post(this::onRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
        toolbar = getView(R.id.toolbar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dytt_more_video;
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.netWorkRequest(type, placeType, page);
    }

    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        ++page;
        presenter.netWorkRequest(type, placeType, page);
    }

    @Override
    public void netWorkSuccess(List<DyttVideoMoreModel> data) {
        if (page == 1) {
            adapter.removeAll();
        }
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

    @Override
    public void onItemClick(View view, int position, DyttVideoMoreModel info) {
        if (ApkUtils.getXLIntent() != null) {
            DyttVideoDetailActivity.startIntent(info.url);
        } else {
            UIUtils.snackBar(getView(R.id.coordinatorLayout), UIUtils.getString(R.string.xl));
        }
    }

    @Override
    public void noMore() {
        UIUtils.snackBar(getView(R.id.coordinatorLayout), getString(R.string.data_empty));
    }
}
