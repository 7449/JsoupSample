package com.image.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framework.utils.UIUtils;
import com.image.R;
import com.image.manager.ApiConfig;
import com.image.ui.fragment.ImageListFragment;

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
