package com.image.image.douban.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.image.R;
import com.image.image.douban.detail.widget.DouBanDetailActivity;
import com.image.image.douban.list.model.DouBanListModel;
import com.image.image.douban.list.presenter.DouBanListPresenter;
import com.image.image.douban.list.presenter.DouBanListPresenterImpl;
import com.image.image.douban.list.view.DouBanListView;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/7/28.
 */
public class DouBanListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, DouBanListView {

    protected int page = 1;

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private DouBanListAdapter adapter;
    private DouBanListPresenter imageListPresenter;

    public static DouBanListFragment newInstance(int position) {
        DouBanListFragment douBanListFragment = new DouBanListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        douBanListFragment.setArguments(bundle);
        return douBanListFragment;
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
        imageListPresenter = new DouBanListPresenterImpl(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    private void initRecyclerView() {
        adapter = new DouBanListAdapter(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLoadingMore(() -> {
            ++page;
            imageListPresenter.netWorkRequest(tabPosition, page);
        });
        adapter.setOnItemClickListener((view, position, info) ->
                DouBanDetailActivity.startIntent(info.detailUrl)
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_douban_list;
    }

    @Override
    public void onRefresh() {
        page = 1;
        imageListPresenter.netWorkRequest(tabPosition, page);
    }


    @Override
    public void netWorkSuccess(List<DouBanListModel> data) {
        if (page == 1) {
            adapter.removeAll();
        }
        adapter.addAll(data);
    }

    @Override
    public void netWorkError(Throwable e) {
        if (getActivity() != null) {
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
        }
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
