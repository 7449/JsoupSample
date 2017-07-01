package com.image.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.image.R;
import com.image.mvp.model.ImageModel;
import com.image.mvp.presenter.SearchListPresenterImpl;
import com.image.mvp.view.ViewManager;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class SearchListActivity extends BaseActivity<SearchListPresenterImpl>
        implements
        SwipeRefreshLayout.OnRefreshListener,
        ViewManager.SearchListView,
        LoadMoreRecyclerView.LoadMoreListener {

    private static final String SEARCH_TYPE = "search";
    private static final String SEARCH_CONTENT = "content";
    private String searchType;
    private String content;

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private XRecyclerViewAdapter<ImageModel> mAdapter;

    private int page = 1;

    public static void start(String searchType, String content) {
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_TYPE, searchType);
        bundle.putString(SEARCH_CONTENT, content);
        UIUtils.startActivity(SearchListActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            searchType = extras.getString(SEARCH_TYPE);
            content = extras.getString(SEARCH_CONTENT);
        }
        setSupportActionBar(toolbar);
        toolbar.setTitle(searchType + " (关键词:" + content + ")");
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapter.setLayoutId(R.layout.item_image_list)
                        .onXBind((holder, position, imageModel) -> ImageLoaderUtils.display(holder.getImageView(R.id.image), imageModel.url))
                        .setOnItemClickListener((view, position, info) -> ImageDetailActivity.startIntent(searchType, info.detailUrl))
        );
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        swipeRefreshLayout = getView(R.id.srf_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected SearchListPresenterImpl initPresenterImpl() {
        return new SearchListPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
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
        mPresenter.netWorkRequest(searchType, content, page = 1);
    }

    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        if (page == mAdapter.getData().get(0).totalPage) {
            UIUtils.snackBar(mStatusView, R.string.data_empty);
            return;
        }
        mPresenter.netWorkRequest(searchType, content, page);
    }

    @Override
    public void netWorkSuccess(List<ImageModel> data) {
        if (mStatusView != null) {
            if (page == 1) {
                mAdapter.removeAll();
            }
            ++page;
            mAdapter.addAllData(data);
        }
    }


    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            if (page != 1) {
                UIUtils.snackBar(mStatusView, R.string.net_error);
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
        if (mStatusView != null) {
            if (page != 1) {
                UIUtils.snackBar(mStatusView, R.string.data_empty);
            } else {
                mAdapter.removeAll();
                setStatusViewStatus(StatusLayout.EMPTY);
            }
        }
    }
}
