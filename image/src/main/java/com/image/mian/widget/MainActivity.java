package com.image.mian.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.image.R;
import com.image.manager.ApiConfig;
import com.image.mian.presenter.MainPresenter;
import com.image.mian.presenter.MainPresenterImpl;
import com.image.mian.view.MainView;
import com.rxjsoupnetwork.manager.RxJsoupSubscriptionManager;

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
        toolbar.setTitle(getString(R.string.dbmz_title));
        switchDouban();
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
            RxJsoupSubscriptionManager.getInstance().clearSubscription();
            super.onBackPressed();
        }
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
    public void switchDouban() {
        replaceFragment(R.id.fragment, TabFragment.newInstance(ApiConfig.Type.DOU_BAN_MEI_ZI));
    }

    @Override
    public void switchMZitu() {
        replaceFragment(R.id.fragment, TabFragment.newInstance(ApiConfig.Type.M_ZI_TU));
    }

    @Override
    public void switchMM() {
        replaceFragment(R.id.fragment, TabFragment.newInstance(ApiConfig.Type.MM));
    }

}
