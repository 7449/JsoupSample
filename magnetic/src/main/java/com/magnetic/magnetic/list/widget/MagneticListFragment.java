package com.magnetic.magnetic.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.magnetic.R;
import com.magnetic.magnetic.list.model.MagneticListModel;
import com.magnetic.magnetic.list.presenter.MagneticListPresenter;
import com.magnetic.magnetic.list.presenter.MagneticListPresenterImpl;
import com.magnetic.magnetic.list.view.MagneticListView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/28
 */

public class MagneticListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MagneticListView {
    protected int page = 1;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MagneticListPresenter presenter;
    private MagneticListAdapter adapter;

    public static MagneticListFragment newInstance(String type, int position) {
        MagneticListFragment listFragment = new MagneticListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, type);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    protected void initBundle() {
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.srf_layout);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        initRecyclerView();
        presenter = new MagneticListPresenterImpl(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    private void initRecyclerView() {
        adapter = new MagneticListAdapter(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLoadingMore(() -> {
            ++page;
            presenter.netWorkRequest("黑猫警长", tabPosition, page);
        });
        adapter.setOnItemClickListener((view, position, info) -> UIUtils.toast(info.url)
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.netWorkRequest("黑猫警长", tabPosition, page);
    }

    @Override
    public void netWorkSuccess(List<MagneticListModel> data) {
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
    public void noMore() {
        UIUtils.snackBar(getView(R.id.coordinatorLayout), getString(R.string.data_empty));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_magnetic_list;
    }
}
