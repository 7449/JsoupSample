package com.movie.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.framework.base.mvp.BaseModel;
import com.movie.R;
import com.movie.mvp.presenter.MainPresenterImpl;
import com.movie.mvp.view.ViewManager;

public class MainActivity extends BaseActivity<MainPresenterImpl>
        implements NavigationView.OnNavigationItemSelectedListener, ViewManager.MainView {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.dytt_title));
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
            drawerLayout.closeDrawer(GravityCompat.START);
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
    public AppCompatActivity getMainActivity() {
        return this;
    }

    @Override
    public void selectMenuFirst() {
        MenuItem item = navigationView.getMenu().findItem(R.id.dytt);
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
