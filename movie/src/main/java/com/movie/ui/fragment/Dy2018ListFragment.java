package com.movie.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.movie.R;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.Dy2018ListPresenterImpl;
import com.movie.mvp.view.ViewManager;
import com.movie.ui.activity.VideoDetailActivity;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class Dy2018ListFragment extends BaseFragment<Dy2018ListPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreRecyclerView.LoadMoreListener,
        ViewManager.Dy2018ListView {

    private int page = 1;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private XRecyclerViewAdapter<MovieModel> mAdapte;

    public static Dy2018ListFragment newInstance(String s, int position) {
        Dy2018ListFragment fragment = new Dy2018ListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, s);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initBundle() {
        super.initBundle();
        type = bundle.getString(FRAGMENT_TYPE);
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.refresh_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected Dy2018ListPresenterImpl initPresenter() {
        return new Dy2018ListPresenterImpl(this);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }

        mAdapte = new XRecyclerViewAdapter<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapte.setLayoutId(R.layout.item_dy2018_main)
                        .onXBind((holder, position, movieModel) -> holder.setTextView(R.id.dy2018_item_content, movieModel.title))
                        .setOnItemClickListener((view, position, info) -> {
                            if (ApkUtils.getXLIntent() != null) {
                                VideoDetailActivity.startIntent(info.detailUrl);
                            } else {
                                UIUtils.snackBar(mStatusView, UIUtils.getString(R.string.xl));
                            }
                        })
        );

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

        setLoad();
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!swipeRefreshLayout.isRefreshing()) {
            onRefresh();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dy2018;
    }

    @Override
    public void onRefresh() {
        mPresenter.netWorkRequest(tabPosition, page = 1);
    }

    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        mPresenter.netWorkRequest(tabPosition, page);
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapte.removeAll();
            }
            ++page;
            mAdapte.addAllData(data);
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            if (page != 1) {
                UIUtils.snackBar(mStatusView, R.string.net_error);
            }
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
        if (mStatusView != null) {
            if (page == 1) {
                mAdapte.removeAll();
                mStatusView.setStatus(StatusLayout.EMPTY);
            } else {
                UIUtils.snackBar(mStatusView, R.string.data_empty);
            }
        }
    }
}
