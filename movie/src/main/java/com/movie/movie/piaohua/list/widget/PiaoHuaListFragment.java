package com.movie.movie.piaohua.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.framework.base.BaseFragment;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.movie.R;
import com.movie.movie.piaohua.detail.widget.PiaoHuaDetailActivity;
import com.movie.movie.piaohua.list.model.PiaoHuaListModel;
import com.movie.movie.piaohua.list.presenter.PiaoHuaListPresenter;
import com.movie.movie.piaohua.list.presenter.PiaoHuaListPresenterImpl;
import com.movie.movie.piaohua.list.view.PiaoHuaListView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/18
 */

public class PiaoHuaListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener,
        BaseRecyclerAdapter.OnItemClickListener<PiaoHuaListModel>,
        PiaoHuaListView {

    private int page = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private PiaoHuaListAdapter adapter;
    private PiaoHuaListPresenter presenter;

    public static PiaoHuaListFragment newInstance(int position) {
        PiaoHuaListFragment fragment = new PiaoHuaListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initBundle() {
        super.initBundle();
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.refresh_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }

        presenter = new PiaoHuaListPresenterImpl(this);

        adapter = new PiaoHuaListAdapter(new ArrayList<>());
        adapter.setOnItemClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_piao_hua;
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.netWorkRequest(tabPosition, page);
    }

    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        ++page;
        presenter.netWorkRequest(tabPosition, page);
    }

    @Override
    public void netWorkSuccess(List<PiaoHuaListModel> data) {
        if (page == 1) {
            adapter.removeAll();
        }
        adapter.addAll(data);
    }

    @Override
    public void netWorkError() {
        if (getActivity() != null)
            UIUtils.INSTANCE.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
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
    public void onItemClick(View view, int position, PiaoHuaListModel info) {
        if (ApkUtils.INSTANCE.getXLIntent() != null) {
            PiaoHuaDetailActivity.startIntent(info.getDetailUrl());
        } else {
            UIUtils.INSTANCE.snackBar(getActivity().findViewById(R.id.coordinatorLayout), UIUtils.INSTANCE.getString(R.string.xl));
        }
    }

    @Override
    public void noMore() {
        if (getActivity() != null)
            UIUtils.INSTANCE.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.data_empty));
    }
}
