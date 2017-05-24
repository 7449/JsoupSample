package com.movie.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.DyttXLMorePresenterImpl;
import com.movie.mvp.presenter.PresenterManager;
import com.movie.mvp.view.ViewManager;
import com.xadapter.adapter.multi.MultiAdapter;
import com.xadapter.adapter.multi.XMultiAdapterListener;
import com.xadapter.holder.XViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttXLMoreActivity extends BaseActivity<DyttXLMorePresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, ViewManager.DyttXLMoreView, XMultiAdapterListener<MovieModel> {
    private static final String TITLE = "title";

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MultiAdapter<MovieModel> mAdapter;
    private static final int TYPE_HEADER = 0;

    public static void startIntent(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        UIUtils.startActivity(DyttXLMoreActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(getIntent().getExtras().getString(TITLE));

        mAdapter = new MultiAdapter<>(new ArrayList<>());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(
                mAdapter.setXMultiAdapterListener(this)
        );

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
    protected DyttXLMorePresenterImpl initPresenterImpl() {
        return new DyttXLMorePresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dytt_more_xl;
    }

    @Override
    public void onRefresh() {
        mPresenter.netWorkRequest(ApiConfig.DYTT_XL);
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
        if (mAdapter.getData() != null) {
            mAdapter.getData().clear();
        }
        mAdapter.addAll(data);
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
    public int multiLayoutId(int viewItemType) {
        switch (viewItemType) {
            case TYPE_HEADER:
                return R.layout.item_dytt_more_xl_header;
            default:
                return R.layout.item_dytt_more_xl_item;
        }
    }

    @Override
    public int getGridLayoutManagerSpanSize(int itemViewType, GridLayoutManager gridManager, int position) {
        return 0;
    }

    @Override
    public boolean getStaggeredGridLayoutManagerFullSpan(int itemViewType) {
        return false;
    }

    @Override
    public void onXMultiBind(XViewHolder holder, MovieModel movieModel, int itemViewType, int position) {
        List<MovieModel> list = mAdapter.getData();

        if (list == null) {
            return;
        }

        switch (itemViewType) {
            case TYPE_HEADER:
                holder.setTextView(R.id.dytt_tv_title, list.get(position).title);
                holder.itemView.setOnClickListener(v -> DyttVideoMoreActivity.startIntent(list.get(position).itemType, ApiConfig.Type.DYTT_XL_TYPE, list.get(position).title));
                break;
            default:
                holder.setTextView(R.id.dytt_item_content, list.get(position).title);
                holder.itemView.setOnClickListener(v -> {
                    if (ApkUtils.getXLIntent() != null) {
                        DyttVideoDetailActivity.startIntent(list.get(position).url);
                    } else {
                        UIUtils.toast(UIUtils.getString(R.string.xl));
                    }
                });
                break;
        }
    }
}
