package com.fiction.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fiction.R;
import com.fiction.mvp.presenter.MainPresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.BaseActivity;
import com.framework.base.mvp.BaseModel;
import com.framework.utils.UIUtils;


public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements NavigationView.OnNavigationItemSelectedListener, ViewManager.MainView {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(R.string.title_81);
        setSupportActionBar(toolbar);
        mPresenter.switchId(MainPresenterImpl.FIRST_FRAGMENT);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        drawerLayout = getView(R.id.dl_layout);
        navigationView = getView(R.id.navigationview);
    }

    @Override
    protected MainPresenterImpl initPresenterImpl() {
        return new MainPresenterImpl(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            mPresenter.onBackPressed();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        toolbar.setTitle(item.getTitle());
        mPresenter.switchId(item.getItemId());
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void switchSearch() {
        new MaterialDialog
                .Builder(this)
                .title(UIUtils.getString(R.string.search_title))
                .inputRange(1, -1)
                .input(
                        UIUtils.getString(R.string.search_dialog_hint),
                        null,
                        (dialog, input) -> SearchActivity.getInstance(String.valueOf(input)))
                .show();
    }

    @Override
    public AppCompatActivity getMainActivity() {
        return this;
    }

    @Override
    public void selectMenuFirst() {
        MenuItem item = navigationView.getMenu().findItem(R.id.fiction_81);
        item.setChecked(true);
        toolbar.setTitle(item.getTitle());
    }

    @Override
    public void onBack() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onMainDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.switchId(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void netWorkSuccess(BaseModel data) {

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
}
