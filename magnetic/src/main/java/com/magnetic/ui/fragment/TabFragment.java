package com.magnetic.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.framework.base.BaseFragment;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;
import com.magnetic.R;
import com.magnetic.manager.ApiConfig;

import io.reactivex.jsoup.network.bus.RxBus;
import io.reactivex.jsoup.network.bus.RxBusCallBack;

/**
 * by y on 2016/7/28.
 */
public class TabFragment extends BaseFragment implements RxBusCallBack<String> {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static TabFragment newInstance(String search) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, search);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }


    @Override
    protected void initBundle() {
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initById() {
        tabLayout = (TabLayout) getView(R.id.tab_layout);
        viewPager = (ViewPager) getView(R.id.viewPager);
    }

    @Override
    protected BasePresenterImpl initPresenter() {
        return null;
    }

    @Override
    protected void initActivityCreated() {
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager(), type);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(tabAdapter.getCount());
        RxBus.getInstance().register(ApiConfig.BUS, this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    public void onBusNext(String s) {
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager(), s));
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onBusError(Throwable throwable) {

    }

    @Override
    public Class<String> busOfType() {
        return String.class;
    }

    @Override
    public void onDestroyView() {
        RxBus.getInstance().unregister(ApiConfig.BUS);
        super.onDestroyView();
    }

    private static class TabAdapter extends FragmentStatePagerAdapter {

        private String[] name;
        private String search;

        TabAdapter(FragmentManager childFragmentManager, String search) {
            super(childFragmentManager);
            this.search = search;
            name = UIUtils.getStringArray(R.array.list_array);
        }

        @Override
        public Fragment getItem(int position) {
            return MagneticListFragment.newInstance(search, position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name[position];
        }


        @Override
        public int getCount() {
            return name.length;
        }
    }

}
