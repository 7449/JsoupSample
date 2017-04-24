package com.fiction.mian.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fiction.R;
import com.fiction.fiction.search.list.widget.SearchListFragment;
import com.fiction.manager.ApiConfig;
import com.fiction.mian.presenter.MainPresenter;
import com.fiction.mian.presenter.MainPresenterImpl;
import com.fiction.mian.view.MainView;
import com.framework.base.BaseActivity;
import com.rxjsoupnetwork.manager.RxJsoupDisposeManager;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainPresenter presenter;
    private AppBarLayout appBarLayout;
    private AppBarLayout.LayoutParams layoutParams;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        layoutParams = (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
        presenter = new MainPresenterImpl(this);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.title_81));
        switch81();
    }

    @Override
    protected void initById() {
        appBarLayout = getView(R.id.appbar);
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
        toolbar.setTitle(item.getTitle());
        if (item.getItemId() == R.id.search) {
            layoutParams.setScrollFlags(0);
        } else {
            layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        presenter.switchId(item.getItemId());
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public void switchSearch() {
        replaceFragment(new SearchListFragment());
    }

    @Override
    public void switch81() {
        replaceFragment(TabFragment.newInstance(ApiConfig.Type.ZW_81));
    }

    @Override
    public void switchKsw() {
        replaceFragment(TabFragment.newInstance(ApiConfig.Type.KSW));
    }

    @Override
    public void switchBiQuGe() {
        replaceFragment(TabFragment.newInstance(ApiConfig.Type.BI_QU_GE));
    }
}
