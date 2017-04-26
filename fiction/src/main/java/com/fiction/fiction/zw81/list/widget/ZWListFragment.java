package com.fiction.fiction.zw81.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fiction.R;
import com.fiction.fiction.zw81.list.model.ZWListModel;
import com.fiction.fiction.zw81.list.presenter.ZWListPresenterImpl;
import com.fiction.fiction.zw81.list.view.ZWListView;
import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/6.
 */

public class ZWListFragment extends BaseFragment
        implements ZWListView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private ZWListAdapter adapter;

    public static ZWListFragment newInstance(String type, int position) {
        ZWListFragment zwListFragment = new ZWListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, type);
        zwListFragment.setArguments(bundle);
        return zwListFragment;
    }

    @Override
    protected void initBundle() {
        super.initBundle();
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
        type = bundle.getString(FRAGMENT_TYPE);
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
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

        adapter = new ZWListAdapter(new ArrayList<>(), type);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zw_list;
    }

    @Override
    public void onRefresh() {
        new ZWListPresenterImpl(this).netWork(tabPosition);
    }

    @Override
    public void netWorkSuccess(List<ZWListModel> data) {
        adapter.removeAll();
        adapter.addAll(data);
    }

    @Override
    public void netWorkError() {
        if (getActivity() != null)
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
}
