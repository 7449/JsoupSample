package com.magnetic.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.framework.base.BaseFragment;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;
import com.magnetic.R;
import com.magnetic.manager.ApiConfig;


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
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager(), type));
        tabLayout.setupWithViewPager(viewPager);
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
                case ApiConfig.Type.MAGNETIC:
                    name = UIUtils.getStringArray(R.array.magnetic_tab);
                    break;
            }
        }

        @Override
        public Fragment getItem(int position) {
            return MagneticListFragment.newInstance(type, position);
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
