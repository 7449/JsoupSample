package com.fiction.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.Collections;
import java.util.List;

/**
 * by y on 2017/4/6.
 */

public class FictionContentsActivity extends BaseActivity
        implements ViewManager.FictionContentsView, OnXBindListener<FictionModel> {

    private static final String URL = "url";
    private static final String TITLE = "title";
    private static final String TYPE = "type";

    private String type = ApiConfig.Type.ZW_81;

    private Toolbar toolbar;
    private ContentLoadingProgressBar progressBar;
    private RecyclerView recyclerView;
    private XRecyclerViewAdapter<FictionModel> mAdapter;


    public static void getInstance(String type, String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        bundle.putString(TYPE, type);
        UIUtils.startActivity(FictionContentsActivity.class, bundle);
    }


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
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
        new FictionContentsPresenterImpl(this).startContents(extras.getString(URL), type);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        recyclerView = getView(R.id.recyclerView);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fiction_list_contents;
    }


    @Override
    public void netWorkSuccess(List<FictionModel> data) {
        Collections.reverse(data);
        mAdapter.addAllData(data);
    }

    @Override
    public void netWorkError() {
        UIUtils.snackBar(getView(R.id.rootView), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        progressBar.show();
    }

    @Override
    public void hideProgress() {
        progressBar.hide();
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
        return true;
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
