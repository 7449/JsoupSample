package com.image.mian.widget;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framework.utils.UIUtils;
import com.image.R;
import com.image.image.douban.list.widget.DouBanListFragment;
import com.image.image.meizitu.list.widget.MZiTuListFragment;
import com.image.image.mm.list.widget.MMListFragment;
import com.image.manager.ApiConfig;

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
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                name = UIUtils.getStringArray(R.array.dbmz_array);
                break;
            case ApiConfig.Type.M_ZI_TU:
                name = UIUtils.getStringArray(R.array.mzitu_array);
                break;
            case ApiConfig.Type.MM:
                name = UIUtils.getStringArray(R.array.mm_array);
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
            case ApiConfig.Type.DOU_BAN_MEI_ZI:
                return DouBanListFragment.newInstance(position);
            case ApiConfig.Type.M_ZI_TU:
                return MZiTuListFragment.newInstance(position);
            case ApiConfig.Type.MM:
                return MMListFragment.newInstance(position);
            default:
                return null;
        }
    }

}
