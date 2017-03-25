package com.fiction.contents.widget;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fiction.R;
import com.fiction.contents.model.ContentsModel;
import com.fiction.contents.presenter.ContentsPresenterImpl;
import com.fiction.contents.view.ContentsView;
import com.fiction.detail.widget.DetailActivity;
import com.framework.base.BaseActivity;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * by y on 2017/1/8.
 */

public class ContentsActivity extends BaseActivity
        implements ContentsView, BaseRecyclerAdapter.OnItemClickListener<ContentsModel> {

    private static final String URL = "url";
    private static final String TITLE = "title";

    private Toolbar toolbar;
    private ContentLoadingProgressBar progressBar;
    private RecyclerView recyclerView;
    private ContentAdapter adapter;

    private List<ContentsModel> list;

    public static void getInstance(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        UIUtils.startActivity(ContentsActivity.class, bundle);
    }


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        toolbar.setTitle(extras.getString(TITLE));
        setSupportActionBar(toolbar);
        list = new ArrayList<>();
        adapter = new ContentAdapter(list);
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        new ContentsPresenterImpl(this).startContents(extras.getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        recyclerView = getView(R.id.recyclerView);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contents;
    }


    @Override
    public void netWorkSuccess(List<ContentsModel> data) {
        Collections.reverse(data);
        adapter.addAll(data);
    }

    @Override
    public void netWorkError(Throwable e) {
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
    public void onItemClick(View view, int position, ContentsModel info) {
        DetailActivity.getInstance(info.detailUrl);
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
