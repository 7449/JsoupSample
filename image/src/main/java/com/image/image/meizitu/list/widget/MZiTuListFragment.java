package com.image.image.meizitu.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.image.R;
import com.image.image.meizitu.detail.widget.MZiTuDetailActivity;
import com.image.image.meizitu.list.model.MZiTuListModel;
import com.image.image.meizitu.list.presenter.MZiTuListPresenter;
import com.image.image.meizitu.list.presenter.MZiTuListPresenterImpl;
import com.image.image.meizitu.list.view.MZiTuListView;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/7/28.
 */
public class MZiTuListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MZiTuListView {

    protected int page = 1;

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private MZiTuListAdapter adapter;
    private MZiTuListPresenter imageListPresenter;

    public static MZiTuListFragment newInstance(int position) {
        MZiTuListFragment mZiTuListFragment = new MZiTuListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        mZiTuListFragment.setArguments(bundle);
        return mZiTuListFragment;
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
        imageListPresenter = new MZiTuListPresenterImpl(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    private void initRecyclerView() {
        adapter = new MZiTuListAdapter(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLoadingMore(() -> {
            ++page;
            imageListPresenter.netWorkRequest(tabPosition, page);
        });
        adapter.setOnItemClickListener((view, position, info) ->
                MZiTuDetailActivity.startIntent(info.detailUrl)
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mzitu_list;
    }

    @Override
    public void onRefresh() {
        page = 1;
        imageListPresenter.netWorkRequest(tabPosition, page);
    }


    @Override
    public void netWorkSuccess(List<MZiTuListModel> data) {
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


    @Override
    public void noMore() {
        if (getActivity() != null) {
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.data_empty));
        }
    }
}
