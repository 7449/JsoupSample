package com.fiction.fiction.search.list.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.fiction.R;
import com.fiction.fiction.search.contents.widget.ContentsActivity;
import com.fiction.db.GreenDaoDbUtils;
import com.fiction.db.SqlBean;
import com.fiction.fiction.search.list.model.SearchModel;
import com.fiction.fiction.search.list.presenter.SearchPresenter;
import com.fiction.fiction.search.list.presenter.SearchPresenterImpl;
import com.fiction.fiction.search.list.view.SearchView;
import com.framework.base.BaseActivity;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.utils.UIUtils;
import com.framework.widget.FlowText;
import com.framework.widget.LoadMoreRecyclerView;
import com.google.android.flexbox.FlexboxLayout;
import com.rxjsoupnetwork.manager.RxJsoupDisposeManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity
        implements View.OnClickListener, SearchView,
        BaseRecyclerAdapter.OnItemClickListener<SearchModel>,
        LoadMoreRecyclerView.LoadMoreListener,
        View.OnFocusChangeListener,
        DialogInterface.OnClickListener {

    private Toolbar mToolbar;
    private SearchPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private SearchAdapter adapter;
    private EditText editText;
    private int page = 0;
    private String fictionName;
    private AlertDialog alertDialog;


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        swipeRefreshLayout.setEnabled(false);
        mToolbar.setTitle(getString(R.string.toolbar_title));
        presenter = new SearchPresenterImpl(this);
        adapter = new SearchAdapter(new ArrayList<>());
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(adapter);
        mToolbar.setOnLongClickListener(v -> {
            GreenDaoDbUtils.clear();
            UIUtils.snackBar(getView(R.id.fa_btn), getString(R.string.db_clear));
            return true;
        });
    }

    @Override
    protected void initById() {
        mToolbar = getView(R.id.toolbar);
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
        getView(R.id.fa_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fa_btn:
                startDialog();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        RxJsoupDisposeManager.getInstance().clearDispose();
        super.onBackPressed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }


    @Override
    public void netWorkSuccess(List<SearchModel> data) {
        if (page == 0) {
            adapter.removeAll();
        }
        adapter.addAll(data);
    }

    @Override
    public void netWorkError() {
        UIUtils.snackBar(getView(R.id.fa_btn), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void fictionNameEmpty() {
        UIUtils.snackBar(getView(R.id.fa_btn), getString(R.string.empty));
    }


    @Override
    public void onItemClick(View view, int position, SearchModel info) {
        ContentsActivity.getInstance(info.detailUrl, info.title);
    }

    @Override
    public void onLoadMore() {
        if (editText != null) {
            ++page;
            presenter.startSearch(fictionName, page);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && alertDialog.getWindow() != null) {
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        fictionName = editText.getText().toString().trim();
        startNetWork();
    }

    private void startNetWork() {
        page = 0;
        presenter.startSearch(fictionName, page);
    }

    private void startDialog() {
        List<SqlBean> fictionNameAll = GreenDaoDbUtils.getFictionNameAll();
        View view = View.inflate(getApplicationContext(), R.layout.dialog_search, null);
        editText = (EditText) view.findViewById(R.id.search_et);

        FlexboxLayout flowLayout = (FlexboxLayout) view.findViewById(R.id.flow);
        flowLayout.removeAllViews();
        alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.fiction_name))
                .setView(view)
                .setNegativeButton(getString(R.string.dialog_unok), null)
                .setPositiveButton(getString(R.string.dialog_ok), this).create();
        alertDialog.show();
        for (int i = 0; i < fictionNameAll.size(); i++) {
            FlowText textView = new FlowText(flowLayout.getContext());
            textView.setText(fictionNameAll.get(i).getTitle());
            textView.setOnClickListener(v -> {
                FlowText flowText = (FlowText) v;
                fictionName = flowText.getText().toString().trim();
                startNetWork();
                alertDialog.dismiss();
            });
            flowLayout.addView(textView);
        }
        editText.setOnFocusChangeListener(this);
    }

    @Override
    public void noMore() {
        UIUtils.snackBar(getView(R.id.fa_btn), getString(R.string.data_empty));
    }
}
