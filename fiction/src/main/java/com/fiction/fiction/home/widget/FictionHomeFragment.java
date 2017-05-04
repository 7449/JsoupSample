package com.fiction.fiction.home.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fiction.R;
import com.fiction.fiction.home.model.FictionHomeModel;
import com.fiction.fiction.home.presenter.FictionHomePresenterImpl;
import com.fiction.fiction.home.view.FictionHomeView;
import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/20
 */

public class FictionHomeFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, FictionHomeView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private FictionHomeAdapter adapter;

    public static FictionHomeFragment newInstance(String type) {
        FictionHomeFragment fragment = new FictionHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.srf_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected void initBundle() {
        super.initBundle();
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

        adapter = new FictionHomeAdapter(new ArrayList<>(), type);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fiction_home;
    }

    @Override
    public void onRefresh() {
        new FictionHomePresenterImpl(this).netWorkRequest(type);
    }

    @Override
    public void netWorkSuccess(List<FictionHomeModel> data) {
        adapter.removeAll();
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
}
