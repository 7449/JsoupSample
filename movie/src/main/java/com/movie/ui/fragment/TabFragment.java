package com.movie.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.framework.base.BaseFragment;
import com.movie.R;
import com.movie.adapter.TabAdapter;
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
        tabLayout = getView(R.id.tab_layout);
        viewPager = getView(R.id.viewPager);
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
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager(), type));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

}
