package com.image.mian.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.framework.base.BaseActivity;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.collection.list.CollectionListFragment;
import com.image.manager.ApiConfig;
import com.image.mian.presenter.MainPresenter;
import com.image.mian.presenter.MainPresenterImpl;
import com.image.mian.view.MainView;
import com.image.search.list.widget.SearchListActivity;
import com.rxjsoupnetwork.manager.RxJsoupDisposeManager;
import com.search.SearchFragment;
import com.search.SearchToActivityInterface;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView, SearchToActivityInterface {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MainPresenter presenter;
    private AppBarLayout appBarLayout;
    private AppBarLayout.LayoutParams layoutParams;
    private String searchType = null;


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        layoutParams = (AppBarLayout.LayoutParams) appBarLayout.getChildAt(0).getLayoutParams();
        presenter = new MainPresenterImpl(this);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle(getString(R.string.dbmz_title));
        setSupportActionBar(toolbar);
        replaceFragment(TabFragment.newInstance(searchType = ApiConfig.Type.DOU_BAN_MEI_ZI));
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFragment searchFragment = (SearchFragment) fragmentManager.findFragmentByTag(SearchFragment.SEARCH_TAG);
        if (searchFragment != null) {
            searchFragment.onBack();
        } else {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                RxJsoupDisposeManager.getInstance().clearDispose();
                super.onBackPressed();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        toolbar.setTitle(item.getTitle());
        if (item.getItemId() == R.id.collection) {
            layoutParams.setScrollFlags(0);
        } else {
            layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
        presenter.switchId(item.getItemId());
        replaceFragment(TabFragment.newInstance(searchType));
        invalidateOptionsMenu();
        drawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(!TextUtils.equals(searchType, ApiConfig.Type.DOU_BAN_MEI_ZI));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                SearchFragment.startFragment(this, this);
                break;
        }
        return true;
    }

    @Override
    public void switchDouban() {
        searchType = ApiConfig.Type.DOU_BAN_MEI_ZI;
    }

    @Override
    public void switchMZitu() {
        searchType = ApiConfig.Type.M_ZI_TU;
    }

    @Override
    public void switchMM() {
        searchType = ApiConfig.Type.MM;
    }

    @Override
    public void switchMeiZiTu() {
        searchType = ApiConfig.Type.MEIZITU;
    }

    @Override
    public void switch7KK() {
        searchType = ApiConfig.Type.KK;
    }

    @Override
    public void switchCollection() {
        replaceFragment(CollectionListFragment.newInstance());
    }

    @Override
    public void onSearchStart(String content) {
        SearchListActivity.start(searchType, content);
    }

    @Override
    public void emptySearch() {
        UIUtils.toast(UIUtils.getString(R.string.search_empty));
    }
}
