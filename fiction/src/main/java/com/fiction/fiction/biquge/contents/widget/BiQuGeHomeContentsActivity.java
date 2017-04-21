package com.fiction.fiction.biquge.contents.widget;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fiction.R;
import com.fiction.fiction.biquge.contents.model.BiQuGeHomeContentsModel;
import com.fiction.fiction.biquge.contents.presenter.BiQuGeHomeContentsPresenterImpl;
import com.fiction.fiction.biquge.contents.view.BiQuGeHomeContentsView;
import com.fiction.fiction.biquge.detail.widget.BiQuGeHomeDetailActivity;
import com.framework.base.BaseActivity;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * by y on 2017/1/8.
 */

public class BiQuGeHomeContentsActivity extends BaseActivity
        implements BiQuGeHomeContentsView, BaseRecyclerAdapter.OnItemClickListener<BiQuGeHomeContentsModel> {

    private static final String URL = "url";
    private static final String TITLE = "title";

    private Toolbar toolbar;
    private ContentLoadingProgressBar progressBar;
    private RecyclerView recyclerView;
    private BiQuGeHomeContentAdapter adapter;

    private List<BiQuGeHomeContentsModel> list;

    public static void getInstance(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        UIUtils.startActivity(BiQuGeHomeContentsActivity.class, bundle);
    }


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        toolbar.setTitle(extras.getString(TITLE));
        setSupportActionBar(toolbar);
        list = new ArrayList<>();
        adapter = new BiQuGeHomeContentAdapter(list);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        new BiQuGeHomeContentsPresenterImpl(this).startContents(extras.getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        recyclerView = getView(R.id.recyclerView);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_biquge_home_contents;
    }


    @Override
    public void netWorkSuccess(List<BiQuGeHomeContentsModel> data) {
        Collections.reverse(data);
        adapter.addAll(data);
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
    public void onItemClick(View view, int position, BiQuGeHomeContentsModel info) {
        BiQuGeHomeDetailActivity.getInstance(info.detailUrl);
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
                Collections.reverse(list);
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }
}
