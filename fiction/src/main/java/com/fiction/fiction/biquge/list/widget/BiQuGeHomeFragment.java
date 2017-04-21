package com.fiction.fiction.biquge.list.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fiction.R;
import com.fiction.fiction.biquge.list.model.BiQuGeHomeModel;
import com.fiction.fiction.biquge.list.presenter.BiQuGeHomePresenterImpl;
import com.fiction.fiction.biquge.list.view.BiQuGeHomeView;
import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/20
 */

public class BiQuGeHomeFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, BiQuGeHomeView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private BiQuGeHomeAdapter adapter;

    public static BiQuGeHomeFragment newInstance() {
        return new BiQuGeHomeFragment();
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

        adapter = new BiQuGeHomeAdapter(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bi_qu_ge_home;
    }

    @Override
    public void onRefresh() {
        new BiQuGeHomePresenterImpl(this).netWork();
    }

    @Override
    public void netWorkSuccess(List<BiQuGeHomeModel> data) {
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
