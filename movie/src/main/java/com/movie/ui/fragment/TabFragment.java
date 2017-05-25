package com.movie.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.framework.base.BaseFragment;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;


/**
 * by y on 2016/7/28.
 */
public class TabFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static TabFragment newInstance(String type) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, type);
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
        switch (type) {
            case ApiConfig.Type.DYTT:
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                break;
            case ApiConfig.Type.DY_2018:
            case ApiConfig.Type.XIAO_PIAN:
            case ApiConfig.Type.PIAO_HUA:
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                break;
        }
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager(), type);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(tabAdapter.getCount());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    private static class TabAdapter extends FragmentPagerAdapter {

        private String[] name;
        private String type;

        TabAdapter(FragmentManager childFragmentManager, String type) {
            super(childFragmentManager);
            this.type = type;
            switch (type) {
                case ApiConfig.Type.DYTT:
                    name = UIUtils.getStringArray(R.array.dytt_tab);
                    break;
                case ApiConfig.Type.DY_2018:
                    name = UIUtils.getStringArray(R.array.dy2018_tab);
                    break;
                case ApiConfig.Type.XIAO_PIAN:
                    name = UIUtils.getStringArray(R.array.xiao_pian_tab);
                    break;
                case ApiConfig.Type.PIAO_HUA:
                    name = UIUtils.getStringArray(R.array.piao_hua_tab);
                    break;
            }
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name[position];
        }

        @Override
        public int getCount() {
            return name.length;
        }


        private Fragment getFragment(int position) {
            switch (type) {
                case ApiConfig.Type.DYTT:
                    if (position == 0) {
                        return DyttNewFragment.newInstance();
                    } else if (position == 1) {
                        return DyttChosenFragment.newInstance();
                    }
                case ApiConfig.Type.DY_2018:
                    return Dy2018ListFragment.newInstance(position);
                case ApiConfig.Type.XIAO_PIAN:
                    return XiaoPianListFragment.newInstance(position);
                case ApiConfig.Type.PIAO_HUA:
                    return PiaoHuaListFragment.newInstance(position);
            }
            return null;
        }
    }

}
