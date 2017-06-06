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
import com.magnetic.mvp.model.MagneticModel;
import com.magnetic.mvp.presenter.MainPresenterImpl;
import com.magnetic.mvp.view.ViewManager;

public class MainActivity extends BaseActivity<MainPresenterImpl> implements ViewManager.MainView {

    private Toolbar mToolbar;

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
                                (dialog, input) -> mPresenter.startSearch(String.valueOf(input)))
                        .show();
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
}
