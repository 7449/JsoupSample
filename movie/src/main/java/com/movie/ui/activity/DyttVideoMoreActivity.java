package com.movie.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.framework.base.BaseActivity;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.DyttVideoMorePresenterImpl;
import com.movie.mvp.view.ViewManager;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttVideoMoreActivity extends BaseActivity<DyttVideoMorePresenterImpl>
        implements ViewManager.DyttVideoMoreView,
        SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener {
    private static final String TYPE = "type";
    private static final String TITLE = "title";
    private static final String PLACE = "place";
    private int page = 1;
    private int placeType = ApiConfig.Type.DYTT_CHOSEN_TYPE;
    private int type = DyttJsoupManager.TYPE_2016;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private XRecyclerViewAdapter<MovieModel> mAdapter;

    public static void startIntent(int type, int placeType, String title) {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bundle.putInt(PLACE, placeType);
        bundle.putString(TITLE, title);
        UIUtils.startActivity(DyttVideoMoreActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        placeType = extras.getInt(PLACE);
        type = extras.getInt(TYPE);
        toolbar.setTitle(extras.getString(TITLE));
        mAdapter = new XRecyclerViewAdapter<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapter.setLayoutId(R.layout.item_dytt_more_video)
                        .onXBind((holder, position, movieModel) -> holder.setTextView(R.id.dytt_item_content, movieModel.title))
                        .setOnItemClickListener((view, position, info) -> {
                            if (ApkUtils.getXLIntent() != null) {
                                DyttVideoDetailActivity.startIntent(info.url);
                            } else {
                                UIUtils.snackBar(getView(R.id.coordinatorLayout), UIUtils.getString(R.string.xl));
                            }
                        })
        );


        swipeRefreshLayout.post(this::onRefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
        toolbar = getView(R.id.toolbar);
    }

    @Override
    protected DyttVideoMorePresenterImpl initPresenterImpl() {
        return new DyttVideoMorePresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dytt_more_video;
    }

    @Override
    public void onRefresh() {
        mPresenter.netWorkRequest(type, placeType, page = 1);
    }

    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        mPresenter.netWorkRequest(type, placeType, page);
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
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
        if (mStatusView != null)
            UIUtils.snackBar(mStatusView, getString(R.string.network_error));
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
        if (mStatusView != null)
            UIUtils.snackBar(mStatusView, getString(R.string.data_empty));
    }
}
