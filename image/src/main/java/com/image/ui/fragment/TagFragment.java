package com.image.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.image.R;
import com.image.mvp.model.ImageModel;
import com.image.mvp.presenter.TagPresenterImpl;
import com.image.mvp.view.ViewManager;
import com.image.ui.activity.TagImageListActivity;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 02/07/2017.
 */

public class TagFragment extends BaseFragment<TagPresenterImpl> implements ViewManager.TagView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private XRecyclerViewAdapter<ImageModel> mAdapter;

    public static TagFragment newInstance(int position) {
        TagFragment tagFragment = new TagFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        tagFragment.setArguments(bundle);
        return tagFragment;
    }


    @Override
    protected void initBundle() {
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
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
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    private void initRecyclerView() {
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_tag)
                        .onXBind((holder, position, imageModel) -> holder.setTextView(R.id.tv_tag, imageModel.title))
                        .setOnItemClickListener((view, position, info) -> TagImageListActivity.startIntent(info.title, info.url))
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_list;
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
        mPresenter.netWorkRequest();
    }

    @Override
    public void netWorkSuccess(List<ImageModel> data) {
        if (isStatusViewNoNull()) {
            mAdapter.removeAll();
            mAdapter.addAllData(data);
        }
    }

    @Override
    public void netWorkError() {
        if (isStatusViewNoNull()) {
            mAdapter.removeAll();
            setStatusViewStatus(StatusLayout.ERROR);
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
    protected TagPresenterImpl initPresenter() {
        return new TagPresenterImpl(this);
    }

    @Override
    public int getTabPosition() {
        return tabPosition;
    }

}
