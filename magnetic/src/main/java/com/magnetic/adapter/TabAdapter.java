package com.magnetic.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framework.utils.UIUtils;
import com.magnetic.R;
import com.magnetic.manager.ApiConfig;
import com.magnetic.ui.fragment.MagneticListFragment;

/**
 * by y on 2016/9/26.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private String[] name;
    private String type;

    public TabAdapter(FragmentManager childFragmentManager, String type) {
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
