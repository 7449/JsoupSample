package com.fiction.mian.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.framework.R;
import com.framework.base.BaseFragment;


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
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager(), type));
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

}
