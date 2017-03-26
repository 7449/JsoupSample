package com.movie.movie.xiaopian.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.framework.base.BaseFragment;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.movie.R;
import com.movie.movie.xiaopian.detail.widget.XiaopianDetailActivity;
import com.movie.movie.xiaopian.list.model.XiaoPianListModel;
import com.movie.movie.xiaopian.list.presenter.XiaoPianListPresenter;
import com.movie.movie.xiaopian.list.presenter.XiaoPianListPresenterImpl;
import com.movie.movie.xiaopian.list.view.XiaoPianListView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class XiaoPianListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener,
        BaseRecyclerAdapter.OnItemClickListener<XiaoPianListModel>,
        XiaoPianListView {

    private int page = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private XiaoPianListAdapter adapter;
    private XiaoPianListPresenter presenter;

    public static XiaoPianListFragment newInstance(int position) {
        XiaoPianListFragment fragment = new XiaoPianListFragment();
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

        presenter = new XiaoPianListPresenterImpl(this);

        adapter = new XiaoPianListAdapter(new ArrayList<>());
        adapter.setOnItemClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xiao_pian;
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
    public void netWorkSuccess(List<XiaoPianListModel> data) {
        if (page == 1) {
            adapter.removeAll();
        }
        adapter.addAll(data);
    }

    @Override
    public void netWorkError(Throwable e) {
        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
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
    public void onItemClick(View view, int position, XiaoPianListModel info) {
        if (ApkUtils.getIntent(ApkUtils.XL) != null) {
            XiaopianDetailActivity.startIntent(info.detailUrl);
        } else {
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), UIUtils.getString(R.string.xl));
        }
    }

    @Override
    public void noMore() {
        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.data_empty));
    }
}
