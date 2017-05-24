package com.movie.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.framework.base.BaseFragment;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.DyttChosenPresenterImpl;
import com.movie.mvp.view.ViewManager;
import com.movie.ui.activity.DyttVideoDetailActivity;
import com.movie.ui.activity.DyttVideoMoreActivity;
import com.movie.ui.activity.DyttXLMoreActivity;
import com.xadapter.adapter.multi.MultiAdapter;
import com.xadapter.adapter.multi.XMultiAdapterListener;
import com.xadapter.holder.XViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttChosenFragment extends BaseFragment<DyttChosenPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, ViewManager.DyttChosenView, XMultiAdapterListener<MovieModel> {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private MultiAdapter<MovieModel> mAdapter;
    private static final int TYPE_HEADER = 0;


    public static DyttChosenFragment newInstance() {
        return new DyttChosenFragment();
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.refresh_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected DyttChosenPresenterImpl initPresenter() {
        return new DyttChosenPresenterImpl(this);
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
        mAdapter = new MultiAdapter<>(new ArrayList<>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(
                mAdapter.setXMultiAdapterListener(this)
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dytt_chosen;
    }

    @Override
    public void onRefresh() {
        mPresenter.netWorkRequest();
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
        if (mAdapter.getData() != null) {
            mAdapter.getData().clear();
        }
        mAdapter.addAll(data);
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
    public int multiLayoutId(int viewItemType) {
        switch (viewItemType) {
            case TYPE_HEADER:
                return R.layout.item_dytt_chosen_header;
            default:
                return R.layout.item_dytt_chosen_item;
        }
    }

    @Override
    public int getGridLayoutManagerSpanSize(int itemViewType, GridLayoutManager gridManager, int position) {
        return 0;
    }

    @Override
    public boolean getStaggeredGridLayoutManagerFullSpan(int itemViewType) {
        return true;
    }

    @Override
    public void onXMultiBind(XViewHolder holder, MovieModel movieModel, int itemViewType, int position) {
        List<MovieModel> list = mAdapter.getData();
        if (list == null) {
            return;
        }
        switch (itemViewType) {
            case TYPE_HEADER:
                holder.setTextView(R.id.dytt_tv_title, list.get(position).title);
                holder.itemView.setOnClickListener(v -> {
                    switch (list.get(position).itemType) {
                        case DyttJsoupManager.TYPE_2016:
                        case DyttJsoupManager.TYPE_ZH:
                        case DyttJsoupManager.TYPE_US:
                        case DyttJsoupManager.TYPE_TV:
                        case DyttJsoupManager.TYPE_ACG:
                            DyttVideoMoreActivity.startIntent(list.get(position).itemType, ApiConfig.Type.DYTT_CHOSEN_TYPE, list.get(position).title);
                            break;
                        case DyttJsoupManager.TYPE_XL:
                            DyttXLMoreActivity.startIntent(list.get(position).title);
                            break;
                    }
                });
                break;
            default:
                holder.setTextView(R.id.dytt_item_content, list.get(position).title);
                holder.itemView.setOnClickListener(v -> {
                    if (ApkUtils.getXLIntent() != null) {
                        DyttVideoDetailActivity.startIntent(list.get(position).url);
                    } else {
                        UIUtils.toast(UIUtils.getString(R.string.xl));
                    }
                });
                break;
        }
    }
}
