package com.image.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.image.R;
import com.image.mvp.model.ImageModel;
import com.image.mvp.presenter.ImageListPresenterImpl;
import com.image.mvp.view.ViewManager;
import com.image.ui.activity.ImageDetailActivity;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;


/**
 * by y on 2016/7/28.
 */
public class ImageListFragment extends BaseFragment<ImageListPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, ViewManager.ImageListView {

    protected int page = 1;

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private XRecyclerViewAdapter<ImageModel> mAdapter;

    public static ImageListFragment newInstance(String type, int position) {
        ImageListFragment imageListFragment = new ImageListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, type);
        imageListFragment.setArguments(bundle);
        return imageListFragment;
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
    protected ImageListPresenterImpl initPresenter() {
        return new ImageListPresenterImpl(this);
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
        recyclerView.setLoadingMore(() -> {
            ++page;
            mPresenter.netWorkRequest(type, tabPosition, page);
        });
        recyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_image_list)
                        .onXBind((holder, position, imageModel) -> ImageLoaderUtils.display(holder.getImageView(R.id.image), imageModel.url))
                        .setOnItemClickListener((view, position, info) -> ImageDetailActivity.startIntent(type, info.detailUrl))
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_list;
    }

    @Override
    public void onRefresh() {
        page = 1;
        mPresenter.netWorkRequest(type, tabPosition, page);
    }


    @Override
    public void netWorkSuccess(List<ImageModel> data) {
        if (page == 1) {
            mAdapter.removeAll();
        }
        mAdapter.addAllData(data);
    }

    @Override
    public void netWorkError() {
        if (getActivity() != null) {
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
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
        if (getActivity() != null) {
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.data_empty));
        }
    }
}
