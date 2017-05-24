package com.movie.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.movie.R;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.Dy2018ListPresenterImpl;
import com.movie.mvp.presenter.PresenterManager;
import com.movie.mvp.view.ViewManager;
import com.movie.ui.activity.Dy2018DetailActivity;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class Dy2018ListFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener,
        ViewManager.Dy2018ListView {

    private int page = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private PresenterManager.Dy2018ListPresenter presenter;
    private XRecyclerViewAdapter<MovieModel> mAdapte;

    public static Dy2018ListFragment newInstance(int position) {
        Dy2018ListFragment fragment = new Dy2018ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initBundle() {
        super.initBundle();
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
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

        presenter = new Dy2018ListPresenterImpl(this);

        mAdapte = new XRecyclerViewAdapter<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapte.setLayoutId(R.layout.item_dy2018_main)
                        .onXBind((holder, position, movieModel) -> holder.setTextView(R.id.dy2018_item_content, movieModel.title))
                        .setOnItemClickListener((view, position, info) -> {
                            if (ApkUtils.getXLIntent() != null) {
                                Dy2018DetailActivity.startIntent(info.detailUrl);
                            } else {
                                UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), UIUtils.getString(R.string.xl));
                            }
                        })
        );

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dy2018;
    }

    @Override
    public void onRefresh() {
        page = 1;
        presenter.netWorkRequest(tabPosition, page);
    }

    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        ++page;
        presenter.netWorkRequest(tabPosition, page);
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
        if (page == 1) {
            mAdapte.removeAll();
        }
        mAdapte.addAllData(data);
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

    @Override
    public void noMore() {
        if (getActivity() != null)
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.data_empty));
    }
}
