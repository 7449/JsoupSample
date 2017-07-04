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
import com.image.mvp.presenter.TagImageListPresenterImpl;
import com.image.mvp.view.ViewManager;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 02/07/2017.
 */

public class TagImageListActivity extends BaseActivity<TagImageListPresenterImpl>
        implements
        SwipeRefreshLayout.OnRefreshListener,
        ViewManager.TagImageListView,
        LoadMoreRecyclerView.LoadMoreListener {

    private static final String URL = "URL";
    private static final String TITLE = "TITLE";

    protected int page = 1;

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private XRecyclerViewAdapter<ImageModel> mAdapter;

    private String url;
    private String title;

    public static void startIntent(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        UIUtils.startActivity(TagImageListActivity.class, bundle);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.srf_layout);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            url = extras.getString(URL);
            title = extras.getString(TITLE);
        }
        initRecyclerView();
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
    }

    private void initRecyclerView() {
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_image_list)
                        .onXBind((holder, position, imageModel) -> ImageLoaderUtils.display(holder.getImageView(R.id.image), imageModel.url))
        );
    }

    @Override
    protected TagImageListPresenterImpl initPresenterImpl() {
        return new TagImageListPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_list;
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
        mPresenter.netWorkRequest(url);
    }


    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        mPresenter.netWorkRequest(url);
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
