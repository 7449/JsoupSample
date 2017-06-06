package com.image.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.framework.base.BaseFragment;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.manager.ApiConfig;


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
        TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager(), type);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(tabAdapter.getCount());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }


    private static class TabAdapter extends FragmentStatePagerAdapter {

        private String[] name;
        private String type;

        TabAdapter(FragmentManager childFragmentManager, String type) {
            super(childFragmentManager);
            this.type = type;
            switch (type) {
                case ApiConfig.Type.DOU_BAN_MEI_ZI:
                    name = UIUtils.getStringArray(R.array.dbmz_array);
                    break;
                case ApiConfig.Type.M_ZI_TU:
                    name = UIUtils.getStringArray(R.array.mzitu_array);
                    break;
                case ApiConfig.Type.MM:
                    name = UIUtils.getStringArray(R.array.mm_array);
                    break;
                case ApiConfig.Type.MEIZITU:
                    name = UIUtils.getStringArray(R.array.meizitu_array);
                    break;
                case ApiConfig.Type.KK:
                    name = UIUtils.getStringArray(R.array.kk_array);
                    break;
            }

        }

        @Override
        public Fragment getItem(int position) {
            return ImageListFragment.newInstance(type, position);
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
