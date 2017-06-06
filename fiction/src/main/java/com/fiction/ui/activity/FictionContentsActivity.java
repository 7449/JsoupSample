package com.fiction.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fiction.R;
import com.fiction.manager.ApiConfig;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.FictionContentsPresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.framework.widget.StatusLayout;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.Collections;
import java.util.List;


/**
 * by y on 2017/4/6.
 */

public class FictionContentsActivity extends BaseActivity<FictionContentsPresenterImpl>
        implements ViewManager.FictionContentsView, OnXBindListener<FictionModel> {

    private static final String URL = "url";
    private static final String TITLE = "title";
    private static final String TYPE = "type";

    private String type = ApiConfig.Type.ZW_81;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private XRecyclerViewAdapter<FictionModel> mAdapter;
    private Toolbar toolbar;
    private Bundle extras;

    public static void getInstance(String type, String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        bundle.putString(TYPE, type);
        UIUtils.startActivity(FictionContentsActivity.class, bundle);
    }


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        swipeRefreshLayout.setEnabled(false);
        extras = getIntent().getExtras();
        type = extras.getString(TYPE);
        toolbar.setTitle(extras.getString(TITLE));
        setSupportActionBar(toolbar);
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_fiction_list_contents)
                        .onXBind(this)
                        .setOnItemClickListener((view, position, info) -> FictionDetailActivity.getInstance(type, info.detailUrl))
        );
        mPresenter.startContents(extras.getString(URL), type);
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!swipeRefreshLayout.isRefreshing()) {
            mPresenter.startContents(extras.getString(URL), type);
        }
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
    }

    @Override
    protected FictionContentsPresenterImpl initPresenterImpl() {
        return new FictionContentsPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fiction_list_contents;
    }


    @Override
    public void netWorkSuccess(List<FictionModel> data) {
        if (mStatusView != null) {
            mStatusView.setStatus(StatusLayout.SUCCESS);
            Collections.reverse(data);
            mAdapter.addAllData(data);
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            mAdapter.removeAll();
            mStatusView.setStatus(StatusLayout.ERROR);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contents_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                Collections.reverse(mAdapter.getData());
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, FictionModel fictionModel) {
        if (position < 10) {
            holder.setTextColor(R.id.contents_tv_, R.color.colorAccent);
        } else {
            holder.setTextColor(R.id.contents_tv_, R.color.black);
        }
        holder.setTextView(R.id.contents_tv_, fictionModel.title);
    }
}
