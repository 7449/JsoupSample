package com.image.image.list.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.framework.base.BaseFragment;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.image.R;
import com.image.image.detail.widget.ImageDetailActivity;
import com.image.image.list.model.ImageListModel;
import com.image.image.list.presenter.ImagePresenter;
import com.image.image.list.presenter.ImagePresenterImpl;
import com.image.image.list.view.ImageView;
import com.image.manager.CorrectUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/7/28.
 */
public class ImageFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, ImageView {

    protected int page = 1;

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageAdapter adapter;
    private ImagePresenter imageListPresenter;

    public static ImageFragment newInstance(int position, String type) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, type);
        imageFragment.setArguments(bundle);
        return imageFragment;
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
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        initRecyclerView();
        imageListPresenter = new ImagePresenterImpl(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    private void initRecyclerView() {
        adapter = new ImageAdapter(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLoadingMore(() -> {
            ++page;
            imageListPresenter.netWorkRequest(tabPosition, page, type);
        });
        adapter.setOnItemClickListener((view, position, info) ->
                ImageDetailActivity.startIntent(info.detailUrl, CorrectUtils.getDetailType(type))
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_list;
    }

    @Override
    public void onRefresh() {
        page = 1;
        imageListPresenter.netWorkRequest(tabPosition, page, type);
    }


    @Override
    public void netWorkSuccess(List<ImageListModel> data) {
        if (page == 1) {
            adapter.removeAll();
        }
        adapter.addAll(data);
    }

    @Override
    public void netWorkError(Throwable e) {
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
    public String getType() {
        return type;
    }

}
