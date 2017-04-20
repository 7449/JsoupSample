package com.fiction.mian.widget;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fiction.R;
import com.fiction.fiction.zw81.list.widget.ZWHomeFragment;
import com.fiction.fiction.zw81.list.widget.ZWListFragment;
import com.fiction.manager.ApiConfig;
import com.framework.utils.UIUtils;

/**
 * by y on 2016/9/26.
 */

class TabAdapter extends FragmentPagerAdapter {

    private String[] name;
    private String type;

    TabAdapter(FragmentManager childFragmentManager, String type) {
        super(childFragmentManager);
        this.type = type;
        switch (type) {
            case ApiConfig.Type.ZW_81:
                name = UIUtils.getStringArray(R.array.tab_zw);
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
            case ApiConfig.Type.ZW_81:
                return position == 0 ? ZWHomeFragment.newInstance() : ZWListFragment.newInstance(position);
            default:
                return null;
        }
    }

}
