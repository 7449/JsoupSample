package com.taobao.main.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.taobao.R;
import com.taobao.main.presenter.MainPresenter;
import com.taobao.main.presenter.MainPresenterImpl;
import com.taobao.main.view.MainView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainPresenter presenter;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        presenter = new MainPresenterImpl(this);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.jkjbaoyou_title));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        drawerLayout = getView(R.id.dl_layout);
        navigationView = getView(R.id.navigationview);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        toolbar.setTitle(item.getTitle());
        presenter.switchId(item.getItemId());
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void switchJKJBaoYou() {

    }
}
