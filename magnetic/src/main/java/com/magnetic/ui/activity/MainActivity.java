package com.magnetic.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.magnetic.R;
import com.magnetic.manager.DBManager;
import com.magnetic.mvp.model.MagneticModel;
import com.magnetic.mvp.model.SearchModel;
import com.magnetic.mvp.presenter.MainPresenterImpl;
import com.magnetic.mvp.view.ViewManager;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.List;

public class MainActivity extends BaseActivity<MainPresenterImpl> implements ViewManager.MainView, OnXBindListener<SearchModel> {

    private Toolbar mToolbar;
    private XRecyclerViewAdapter<SearchModel> adapter;
    private MaterialDialog markDialog;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initById() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected MainPresenterImpl initPresenterImpl() {
        return new MainPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                new MaterialDialog
                        .Builder(this)
                        .title(UIUtils.getString(R.string.search_title))
                        .inputRange(1, -1)
                        .input(
                                UIUtils.getString(R.string.search_dialog_hint),
                                null,
                                ((dialog, input) -> {
                                    if (DBManager.isEmpty(String.valueOf(input))) {
                                        DBManager.insert(String.valueOf(input));
                                    }
                                    mPresenter.startSearch(String.valueOf(input));
                                }))
                        .show();
                break;
            case R.id.mark:
                List<SearchModel> fictionMarkAll = DBManager.getSearchContent();
                if (fictionMarkAll == null || fictionMarkAll.isEmpty()) {
                    UIUtils.snackBar(mStatusView, R.string.mark_null);
                } else {
                    if (adapter == null) {
                        adapter = new XRecyclerViewAdapter<>();
                    }
                    markDialog = new MaterialDialog
                            .Builder(this)
                            .title(R.string.mark_title)
                            .adapter(
                                    adapter
                                            .initXData(fictionMarkAll)
                                            .setLayoutId(R.layout.item_mark)
                                            .onXBind(this)
                                            .setOnItemClickListener((view, position, info) -> {
                                                        markDialog.dismiss();
                                                        mPresenter.startSearch(String.valueOf(info.getSearchContent()));
                                                    }
                                            )
                                    , null)
                            .show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onMainDestroy();
        super.onDestroy();
    }

    @Override
    public void netWorkSuccess(MagneticModel data) {

    }

    @Override
    public void netWorkError() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public FragmentActivity getMainActivity() {
        return this;
    }

    @Override
    public void onXBind(XViewHolder holder, int position, SearchModel searchModel) {
        holder.setTextView(R.id.tv_name, searchModel.getSearchContent());
        holder.getView(R.id.iv_delete).setOnClickListener(v -> {
            DBManager.clear(searchModel.getSearchContent());
            adapter.remove(position);
            if (DBManager.getSearchContent() == null || DBManager.getSearchContent().isEmpty()) {
                if (markDialog != null) {
                    markDialog.dismiss();
                    UIUtils.snackBar(mStatusView, R.string.mark_null);
                }
            }
        });
    }
}
