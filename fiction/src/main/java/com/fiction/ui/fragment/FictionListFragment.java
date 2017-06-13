package com.fiction.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fiction.R;
import com.fiction.manager.JsoupFictionListManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.FictionListPresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.fiction.ui.activity.FictionContentsActivity;
import com.framework.base.BaseFragment;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.xadapter.adapter.multi.MultiAdapter;
import com.xadapter.adapter.multi.XMultiAdapterListener;
import com.xadapter.holder.XViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/6.
 */

public class FictionListFragment extends BaseFragment<FictionListPresenterImpl>
        implements ViewManager.FictionListView, SwipeRefreshLayout.OnRefreshListener, XMultiAdapterListener<FictionModel> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private MultiAdapter<FictionModel> mAdapter;

    public static FictionListFragment newInstance(String type, int position) {
        FictionListFragment biQuGeListFragment = new FictionListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, type);
        biQuGeListFragment.setArguments(bundle);
        return biQuGeListFragment;
    }

    @Override
    protected void initBundle() {
        super.initBundle();
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.refresh_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected FictionListPresenterImpl initPresenter() {
        return new FictionListPresenterImpl(this);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        swipeRefreshLayout.setEnabled(false);
        mAdapter = new MultiAdapter<>(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(
                mAdapter.setXMultiAdapterListener(this)
        );
        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fiction_list;
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
        mPresenter.netWork(type, tabPosition);
    }

    @Override
    public void netWorkSuccess(List<FictionModel> data) {
        if (mStatusView != null) {
            if (mAdapter.getData() != null) {
                mAdapter.getData().clear();
            }
            mAdapter.addAll(data);
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            mAdapter.clearAll();
            UIUtils.snackBar(mStatusView, getString(R.string.network_error));
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
    public int multiLayoutId(int viewItemType) {
        switch (viewItemType) {
            case JsoupFictionListManager.TYPE_HEADER:
                return R.layout.item_fiction_list_header;
            case JsoupFictionListManager.TYPE_TITLE:
                return R.layout.item_fiction_list_title;
            default:
                return R.layout.item_fiction_list_item;
        }
    }

    @Override
    public int getGridLayoutManagerSpanSize(int itemViewType, GridLayoutManager gridManager, int position) {
        return 0;
    }

    @Override
    public boolean getStaggeredGridLayoutManagerFullSpan(int itemViewType) {
        return itemViewType == JsoupFictionListManager.TYPE_HEADER
                || itemViewType == JsoupFictionListManager.TYPE_TITLE;
    }

    @Override
    public void onXMultiBind(XViewHolder holder, FictionModel fictionModel, int itemViewType, int position) {
        if (mAdapter.getData() == null) {
            return;
        }
        FictionModel biqugeListModel = mAdapter.getData().get(position);
        switch (itemViewType) {
            case JsoupFictionListManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, biqugeListModel.title);
                holder.setTextView(R.id.tv_content, biqugeListModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), biqugeListModel.url);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, biqugeListModel.detailUrl, biqugeListModel.title));

                break;
            case JsoupFictionListManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, biqugeListModel.title);
                break;
            case JsoupFictionListManager.TYPE_UPDATE:
            case JsoupFictionListManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, biqugeListModel.title);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, biqugeListModel.detailUrl, biqugeListModel.title));
                break;
        }
    }
}
