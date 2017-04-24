package com.fiction.fiction.ksw.list.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fiction.R;
import com.fiction.fiction.ksw.list.model.KswHomeModel;
import com.fiction.fiction.ksw.list.presenter.KswHomePresenterImpl;
import com.fiction.fiction.ksw.list.view.KswHomeView;
import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/20
 */

public class KswHomeFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, KswHomeView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private KswHomeAdapter adapter;

    public static KswHomeFragment newInstance() {
        return new KswHomeFragment();
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

        adapter = new KswHomeAdapter(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ksw_home;
    }

    @Override
    public void onRefresh() {
        new KswHomePresenterImpl(this).netWork();
    }

    @Override
    public void netWorkSuccess(List<KswHomeModel> data) {
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
