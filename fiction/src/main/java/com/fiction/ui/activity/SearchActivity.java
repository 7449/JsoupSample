package com.fiction.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.SearchListPresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.BaseActivity;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/5/25.
 */

public class SearchActivity extends BaseActivity<SearchListPresenterImpl>
        implements ViewManager.SearchListView, LoadMoreRecyclerView.LoadMoreListener {

    private static final String TYPE_KEY = "content";
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private Toolbar toolbar;
    private int page = 0;
    private String fictionName;
    private XRecyclerViewAdapter<FictionModel> mAdapter;

    public static void getInstance(String content) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_KEY, content);
        UIUtils.startActivity(SearchActivity.class, bundle);
    }


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        fictionName = getIntent().getExtras().getString(TYPE_KEY);
        swipeRefreshLayout.setEnabled(false);
        toolbar.setTitle(fictionName);
        setSupportActionBar(toolbar);
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapter.setLayoutId(R.layout.item_search)
                        .onXBind((holder, position, fictionModel) -> {
                            ImageLoaderUtils.display(holder.getImageView(R.id.search_iv_img), fictionModel.url);
                            holder.setTextView(R.id.search_tv_title, fictionModel.title);
                            holder.setTextView(R.id.search_tv_content, fictionModel.message);
                        })
                        .setOnItemClickListener((view, position, info) -> FictionContentsActivity.getInstance(ApiConfig.Type.ZW_81, info.detailUrl, info.title))
        );
        mPresenter.startSearch(fictionName, page = 0);
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!swipeRefreshLayout.isRefreshing()) {
            mPresenter.startSearch(fictionName, page = 0);
        }
    }

    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        mPresenter.startSearch(fictionName, page);
    }

    @Override
    public void netWorkSuccess(List<FictionModel> data) {
        if (mStatusView != null) {
            if (page == 0) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(data);
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            if (page != 0) {
                UIUtils.snackBar(mStatusView, R.string.net_error);
            }
        }
    }

    @Override
    public void showProgress() {
        if (mStatusView != null)
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (mStatusView != null)
            swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void noMore() {
        if (mStatusView != null) {
            if (page != 0) {
                UIUtils.snackBar(mStatusView, R.string.data_empty);
            }
        }
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
        toolbar = getView(R.id.toolbar);
    }

    @Override
    protected SearchListPresenterImpl initPresenterImpl() {
        return new SearchListPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }
}
