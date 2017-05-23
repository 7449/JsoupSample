package com.magnetic.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.magnetic.R;
import com.magnetic.manager.ApiConfig;
import com.magnetic.mvp.presenter.MainPresenterImpl;
import com.magnetic.mvp.presenter.PresenterManager;
import com.magnetic.mvp.view.ViewManager;
import com.magnetic.ui.fragment.TabFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, ViewManager.MainView {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private PresenterManager.MainPresenter presenter;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        presenter = new MainPresenterImpl(this);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        switchMagnetic();
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
    public void switchMagnetic() {
        replaceFragment(TabFragment.newInstance(ApiConfig.Type.MAGNETIC));
    }
}
