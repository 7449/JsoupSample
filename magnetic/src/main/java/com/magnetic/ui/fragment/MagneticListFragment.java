package com.magnetic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.magnetic.R;
import com.magnetic.mvp.model.MagneticModel;
import com.magnetic.mvp.presenter.MagneticListPresenterImpl;
import com.magnetic.mvp.view.ViewManager;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/4/28
 */

public class MagneticListFragment extends BaseFragment<MagneticListPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, ViewManager.MagneticListView, MagneticSearchDialogFragment.MagneticListener {
    protected int page = 1;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private XRecyclerViewAdapter<MagneticModel> mAdapter;

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
    protected MagneticListPresenterImpl initPresenter() {
        return new MagneticListPresenterImpl(this);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        initRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    private void initRecyclerView() {
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLoadingMore(() -> {
            ++page;
            mPresenter.netWorkRequest("黑猫警长", tabPosition, page);
        });
        recyclerView.setAdapter(
                mAdapter.setLayoutId(R.layout.item_magnetic_list)
                        .onXBind((holder, position, magneticModel) -> holder.setTextView(R.id.list_tv, magneticModel.message))
                        .setOnItemClickListener((view, position, info) -> {

                        })
        );
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.netWorkRequest("黑猫警长", tabPosition, page);
    }

    @Override
    public void netWorkSuccess(List<MagneticModel> data) {
        if (page == 1) {
            mAdapter.removeAll();
        }
        mAdapter.addAllData(data);
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

    @Override
    public void onSearchNext(@NonNull String content) {
        if (TextUtils.isEmpty(content)) {
            UIUtils.snackBar(getView(R.id.coordinatorLayout), getString(R.string.search_empty));
        } else {

        }
    }

    @Override
    public void onSearchCancel() {

    }
}
