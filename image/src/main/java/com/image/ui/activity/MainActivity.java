package com.image.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.framework.base.BaseActivity;
import com.framework.base.mvp.BaseModel;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.manager.ApiConfig;
import com.image.mvp.presenter.MainPresenterImpl;
import com.image.mvp.view.ViewManager;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements NavigationView.OnNavigationItemSelectedListener, ViewManager.MainView {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private AppBarLayout.LayoutParams layoutParams;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        layoutParams = (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.dbmz_title));
        setSupportActionBar(toolbar);
        mPresenter.switchId(MainPresenterImpl.FIRST_FRAGMENT);
    }

    @Override
    protected void initById() {
        appBarLayout = getView(R.id.appbar);
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
        if (item.getItemId() == R.id.collection) {
            layoutParams.setScrollFlags(0);
        } else {
            layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public AppCompatActivity getMainActivity() {
        return this;
    }

    @Override
    public void selectMenuFirst() {
        MenuItem item = navigationView.getMenu().findItem(R.id.dbmz);
        item.setChecked(true);
        toolbar.setTitle(item.getTitle());
    }

    @Override
    protected void onDestroy() {
        mPresenter.onMainDestroy();
        super.onDestroy();
    }

    @Override
    public void onBack() {
        super.onBackPressed();
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
                        (dialog, input) -> SearchListActivity.start(ApiConfig.Type.M_ZI_TU, String.valueOf(input)))
                .show();
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
