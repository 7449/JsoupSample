package com.movie.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framework.base.BaseFragment;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.DyttNewPresenterImpl;
import com.movie.mvp.view.ViewManager;
import com.movie.ui.activity.DyttVideoDetailActivity;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/3/23
 * <p>
 * 最新发布170部影视 Fragment
 */

public class DyttNewFragment extends BaseFragment<DyttNewPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener,
        ViewManager.DyttNewView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private XRecyclerViewAdapter<MovieModel> mAdapter;

    public static DyttNewFragment newInstance(String concat) {
        DyttNewFragment dyttNewFragment = new DyttNewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, concat);
        dyttNewFragment.setArguments(bundle);
        return dyttNewFragment;
    }

    @Override
    protected void initBundle() {
        super.initBundle();
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.refresh_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected DyttNewPresenterImpl initPresenter() {
        return new DyttNewPresenterImpl(this);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(mAdapter
                .setLayoutId(R.layout.item_dytt_new)
                .onXBind((holder, position, movieModel) -> holder.setTextView(R.id.tv_dytt_new, movieModel.title))
                .setOnItemClickListener((view, position, info) -> {
                    if (ApkUtils.getXLIntent() != null) {
                        DyttVideoDetailActivity.startIntent(info.detailUrl);
                    } else {
                        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), UIUtils.getString(R.string.xl));
                    }
                })
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dytt_new;
    }

    @Override
    public void onRefresh() {
        mPresenter.netWorkRequest();
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
        if (mStatusView != null) {
            mAdapter.removeAll();
            data.remove(0);
            mAdapter.addAllData(data);
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null)
            UIUtils.snackBar(mStatusView, getString(R.string.network_error));
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
