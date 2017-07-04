package com.fiction.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupPtFictionHomeManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.PtFictionHomePresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.fiction.ui.activity.FictionContentsActivity;
import com.fiction.ui.activity.PtFictionMoreActivity;
import com.framework.base.BaseFragment;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.socks.library.KLog;
import com.xadapter.adapter.multi.MultiAdapter;
import com.xadapter.adapter.multi.XMultiAdapterListener;
import com.xadapter.holder.XViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 03/07/2017.
 */

public class PtFictionHomeFragment extends BaseFragment<PtFictionHomePresenterImpl>
        implements ViewManager.PtFictionHomeView, SwipeRefreshLayout.OnRefreshListener, XMultiAdapterListener<FictionModel> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;

    private MultiAdapter<FictionModel> mAdapter;

    public static PtFictionHomeFragment newInstance() {
        return new PtFictionHomeFragment();
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.srf_layout);
        recyclerView = getView(R.id.recyclerView);
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
        return R.layout.fragment_fiction_home;
    }

    @Override
    protected PtFictionHomePresenterImpl initPresenter() {
        return new PtFictionHomePresenterImpl(this);
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
    public void netWorkSuccess(List<FictionModel> data) {
        if (isStatusViewNoNull()) {
            mAdapter.clearAll();
            mAdapter.addAll(data);
        }
    }

    @Override
    public void netWorkError() {
        if (isStatusViewNoNull()) {
            mAdapter.clearAll();
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
    public int multiLayoutId(int viewItemType) {
        switch (viewItemType) {
            case JsoupPtFictionHomeManager.TYPE_TITLE_MORE:
            case JsoupPtFictionHomeManager.TYPE_TITLE:
                return R.layout.item_fiction_home_title;
            case JsoupPtFictionHomeManager.TYPE_HEADER:
                return R.layout.item_fiction_home_header;
            case JsoupPtFictionHomeManager.TYPE_HOT:
                return R.layout.item_fiction_home_hot;
            case JsoupPtFictionHomeManager.TYPE_ITEM:
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
        return itemViewType == JsoupPtFictionHomeManager.TYPE_HEADER
                || itemViewType == JsoupPtFictionHomeManager.TYPE_TITLE
                || itemViewType == JsoupPtFictionHomeManager.TYPE_TITLE_MORE;
    }

    @Override
    public void onXMultiBind(XViewHolder holder, FictionModel fictionModel, int itemViewType, int position) {
        if (mAdapter.getData() == null) {
            return;
        }
        FictionModel kswHomeModel = mAdapter.getData().get(position);
        switch (itemViewType) {
            case JsoupPtFictionHomeManager.TYPE_HEADER:
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.setTextView(R.id.tv_content, kswHomeModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), kswHomeModel.url);
                holder.itemView.setOnClickListener(v -> startFictionContents(fictionModel));
                break;
            case JsoupPtFictionHomeManager.TYPE_TITLE:
                holder.getView(R.id.tv_more).setVisibility(View.GONE);
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                break;
            case JsoupPtFictionHomeManager.TYPE_HOT:
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), kswHomeModel.url);
                holder.itemView.setOnClickListener(v -> startFictionContents(fictionModel));
                break;
            case JsoupPtFictionHomeManager.TYPE_ITEM:
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.itemView.setOnClickListener(v -> startFictionContents(fictionModel));
                break;
            case JsoupPtFictionHomeManager.TYPE_TITLE_MORE:
                holder.getView(R.id.tv_more).setVisibility(View.VISIBLE);
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.itemView.setOnClickListener(v -> PtFictionMoreActivity.getInstance(fictionModel.title, fictionModel.detailUrl));
                break;
        }
    }

    private void startFictionContents(FictionModel fictionModel) {
        String detailUrl = fictionModel.detailUrl;
        if (TextUtils.isEmpty(detailUrl)) {
            KLog.d();
            UIUtils.snackBar(coordinatorLayout, R.string.pt_list_content_error);
            return;
        }
        String bookinfo = detailUrl.replaceAll(".html", "/index.html").replaceAll("bookinfo", "html");
        if (TextUtils.isEmpty(bookinfo)) {
            KLog.d();
            UIUtils.snackBar(coordinatorLayout, R.string.pt_list_content_error);
            return;
        }
        FictionContentsActivity.getInstance(ApiConfig.Type.PIAO_TIAN, bookinfo, fictionModel.title);
    }
}
