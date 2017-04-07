package com.movie.main.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.movie.R;
import com.movie.main.presenter.MainPresenter;
import com.movie.main.presenter.MainPresenterImpl;
import com.movie.main.view.MainView;
import com.movie.main.widget.tab.TabFragment;
import com.movie.manager.ApiConfig;
import com.rxjsoupnetwork.manager.RxJsoupDisposeManager;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainPresenter presenter;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        presenter = new MainPresenterImpl(this);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.dytt_title));
        switchDytt();
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        drawerLayout = getView(R.id.dl_layout);
        navigationView = getView(R.id.navigationview);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            RxJsoupDisposeManager.getInstance().clearDispose();
            super.onBackPressed();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        RxJsoupDisposeManager.getInstance().clearDispose();
        toolbar.setTitle(item.getTitle());
        presenter.switchId(item.getItemId());
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void switchDytt() {
        replaceFragment(R.id.fragment, TabFragment.newInstance(ApiConfig.Type.DYTT));
    }

    @Override
    public void swichDy2018() {
        replaceFragment(R.id.fragment, TabFragment.newInstance(ApiConfig.Type.DY_2018));
    }

    @Override
    public void switchXiaoPian() {
        replaceFragment(R.id.fragment, TabFragment.newInstance(ApiConfig.Type.XIAO_PIAN));
    }
}
