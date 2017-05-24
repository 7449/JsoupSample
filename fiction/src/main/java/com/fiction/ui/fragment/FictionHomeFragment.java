package com.fiction.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionHomeManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.FictionHomePresenterImpl;
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

import io.reactivex.jsoup.network.bus.RxBus;

/**
 * by y on 2017/4/20
 */

public class FictionHomeFragment extends BaseFragment<FictionHomePresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener,
        ViewManager.FictionHomeView, XMultiAdapterListener<FictionModel> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private MultiAdapter<FictionModel> mAdapter;

    public static FictionHomeFragment newInstance(String type) {
        FictionHomeFragment fragment = new FictionHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.srf_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected FictionHomePresenterImpl initPresenter() {
        return new FictionHomePresenterImpl(this);
    }

    @Override
    protected void initBundle() {
        super.initBundle();
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);

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
        return R.layout.fragment_fiction_home;
    }

    @Override
    public void onRefresh() {
        mPresenter.netWorkRequest(type);
    }

    @Override
    public void netWorkSuccess(List<FictionModel> data) {
        if (mAdapter.getData() != null) {
            mAdapter.getData().clear();
        }
        mAdapter.addAll(data);
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
    public int multiLayoutId(int viewItemType) {
        switch (viewItemType) {
            case JsoupFictionHomeManager.TYPE_HEADER:
                return R.layout.item_fiction_home_header;
            case JsoupFictionHomeManager.TYPE_TITLE:
                return R.layout.item_fiction_home_title;
            default:
                return R.layout.item_fiction_home_item;
        }
    }

    @Override
    public int getGridLayoutManagerSpanSize(int itemViewType, GridLayoutManager gridManager, int position) {
        return 0;
    }

    @Override
    public boolean getStaggeredGridLayoutManagerFullSpan(int itemViewType) {
        return itemViewType == JsoupFictionHomeManager.TYPE_HEADER
                || itemViewType == JsoupFictionHomeManager.TYPE_TITLE;
    }

    @Override
    public void onXMultiBind(XViewHolder holder, FictionModel fictionModel, int itemViewType, int position) {
        if (mAdapter.getData() == null) {
            return;
        }
        FictionModel kswHomeModel = mAdapter.getData().get(position);
        switch (itemViewType) {
            case JsoupFictionHomeManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.setTextView(R.id.tv_content, kswHomeModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), kswHomeModel.url);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, kswHomeModel.detailUrl, kswHomeModel.title));

                break;
            case JsoupFictionHomeManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.itemView.setOnClickListener(v -> RxBus.getInstance().post(ApiConfig.Type.BI_QU_GE, kswHomeModel.title));
                break;
            case JsoupFictionHomeManager.TYPE_HOT:
            case JsoupFictionHomeManager.TYPE_CENTER:
            case JsoupFictionHomeManager.TYPE_RECENT:
            case JsoupFictionHomeManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, kswHomeModel.detailUrl, kswHomeModel.title));
                break;
        }
    }
}
