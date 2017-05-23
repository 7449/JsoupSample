package com.fiction.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fiction.R;
import com.fiction.ui.fragment.FictionHomeFragment;
import com.fiction.ui.fragment.FictionListFragment;
import com.fiction.manager.ApiConfig;
import com.framework.utils.UIUtils;

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
            case ApiConfig.Type.ZW_81:
                name = UIUtils.getStringArray(R.array.tab_zw);
                break;
            case ApiConfig.Type.BI_QU_GE:
                name = UIUtils.getStringArray(R.array.tab_bi_qu_ge);
                break;
            case ApiConfig.Type.KSW:
                name = UIUtils.getStringArray(R.array.tab_ksw);
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
            case ApiConfig.Type.BI_QU_GE:
            case ApiConfig.Type.KSW:
                return position == 0 ? FictionHomeFragment.newInstance(type) : FictionListFragment.newInstance(type, position);
            default:
                return null;
        }
    }

}
