package com.fiction.fiction.zw81.list.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.fiction.R;
import com.fiction.fiction.zw81.list.model.ZWHomeModel;
import com.fiction.fiction.zw81.list.presenter.ZWHomePresenterImpl;
import com.fiction.fiction.zw81.list.view.ZWHomeView;
import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/20
 */

public class ZWHomeFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, ZWHomeView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private ZWHomeAdapter adapter;

    public static ZWHomeFragment newInstance() {
        return new ZWHomeFragment();
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.srf_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

        adapter = new ZWHomeAdapter(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zw_home;
    }

    @Override
    public void onRefresh() {
        new ZWHomePresenterImpl(this).netWork();
    }

    @Override
    public void netWorkSuccess(List<ZWHomeModel> data) {
//        adapter.removeAll();
//        adapter.addAll(data);
    }

    @Override
    public void netWorkError() {
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
