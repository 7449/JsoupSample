package com.fiction.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.PtFictionMorePresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 04/07/2017.
 */

public class PtFictionMoreActivity extends BaseActivity<PtFictionMorePresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, LoadMoreRecyclerView.LoadMoreListener, ViewManager.PtFictionMoreView {

    private static final String TYPE_URL = "url";
    private static final String TYPE_TITLE = "title";

    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private XRecyclerViewAdapter<FictionModel> mAdapter;
    private Toolbar toolbar;
    private int page = 1;
    private String url;
    private String title;

    public static void getInstance(String title, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_URL, url);
        bundle.putString(TYPE_TITLE, title);
        UIUtils.startActivity(PtFictionMoreActivity.class, bundle);
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
        toolbar = getView(R.id.toolbar);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            url = extras.getString(TYPE_URL);
            title = extras.getString(TYPE_TITLE);
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        initRecyclerView();
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
                        .setLayoutId(R.layout.item_fiction_pt_list)
                        .setOnItemClickListener((view, position, info) -> {
                            String detailUrl = info.detailUrl;
                            if (TextUtils.isEmpty(detailUrl)) {
                                UIUtils.snackBar(mStatusView, R.string.pt_list_content_error);
                                return;
                            }
                            String bookinfo = detailUrl.replaceAll(".html", "/index.html").replaceAll("bookinfo", "html");
                            if (TextUtils.isEmpty(bookinfo)) {
                                UIUtils.snackBar(mStatusView, R.string.pt_list_content_error);
                                return;
                            }
                            FictionContentsActivity.getInstance(ApiConfig.Type.PIAO_TIAN, bookinfo, info.title);
                        })
                        .onXBind((holder, position, fictionModel) -> holder.setTextView(R.id.tv_title, fictionModel.title))
        );
    }


    @Override
    protected PtFictionMorePresenterImpl initPresenterImpl() {
        return new PtFictionMorePresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fiction_pt_more;
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
        mPresenter.netWork(url, page = 1);
    }


    @Override
    public void onLoadMore() {
        if (swipeRefreshLayout.isRefreshing()) {
            return;
        }
        mPresenter.netWork(url, page);
    }

    @Override
    public void netWorkSuccess(List<FictionModel> data) {
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
