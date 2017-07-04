package com.fiction.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.PtFictionListPresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.fiction.ui.activity.FictionContentsActivity;
import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 03/07/2017.
 */

public class PtFictionListFragment extends BaseFragment<PtFictionListPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, ViewManager.PtFictionListView, LoadMoreRecyclerView.LoadMoreListener {

    protected int page = 1;

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private XRecyclerViewAdapter<FictionModel> mAdapter;

    public static PtFictionListFragment newInstance(int position) {
        PtFictionListFragment fragment = new PtFictionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle() {
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
    }

    @Override
    protected PtFictionListPresenterImpl initPresenter() {
        return new PtFictionListPresenterImpl(this);
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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_fiction_pt_list)
                        .setOnItemClickListener((view, position, info) -> {
                            String detailUrl = info.detailUrl;
                            if (TextUtils.isEmpty(detailUrl)) {
                                UIUtils.snackBar(coordinatorLayout, R.string.pt_list_content_error);
                                return;
                            }
                            String bookinfo = detailUrl.replaceAll(".html", "/index.html").replaceAll("bookinfo", "html");
                            if (TextUtils.isEmpty(bookinfo)) {
                                UIUtils.snackBar(coordinatorLayout, R.string.pt_list_content_error);
                                return;
                            }
                            FictionContentsActivity.getInstance(ApiConfig.Type.PIAO_TIAN, bookinfo, info.title);
                        })
                        .onXBind((holder, position, fictionModel) -> holder.setTextView(R.id.tv_title, fictionModel.title))
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pt_fiction_list;
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!swipeRefreshLayout.isRefreshing()) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        setStatusViewStatus(StatusLayout.SUCCESS);
        mPresenter.netWork(tabPosition, page = 1);
    }


    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        mPresenter.netWork(tabPosition, page);
    }

    @Override
    public void netWorkSuccess(List<FictionModel> data) {
        if (isStatusViewNoNull()) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(data);
        }
    }

    @Override
    public void netWorkError() {
        if (isStatusViewNoNull()) {
            if (page != 1) {
                UIUtils.snackBar(coordinatorLayout, R.string.net_error);
            } else {
                mAdapter.removeAll();
                setStatusViewStatus(StatusLayout.ERROR);
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
        if (isStatusViewNoNull()) {
            if (page != 1) {
                UIUtils.snackBar(coordinatorLayout, R.string.data_empty);
            } else {
                mAdapter.removeAll();
                setStatusViewStatus(StatusLayout.EMPTY);
            }
        }
    }

}
