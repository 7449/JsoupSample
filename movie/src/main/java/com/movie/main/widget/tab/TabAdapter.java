package com.movie.main.widget.tab;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.movie.dy2018.list.widget.Dy2018ListFragment;
import com.movie.movie.dytt.list.widget.DyttChosenFragment;
import com.movie.movie.dytt.list.widget.DyttNewFragment;
import com.movie.movie.xiaopian.list.widget.XiaoPianListFragment;

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
            case ApiConfig.Type.DYTT:
                name = UIUtils.getStringArray(R.array.dytt_tab);
                break;
            case ApiConfig.Type.DY_2018:
                name = UIUtils.getStringArray(R.array.dy2018_tab);
                break;
            case ApiConfig.Type.XIAO_PIAN:
                name = UIUtils.getStringArray(R.array.xiao_pian_tab);
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
        }
        return null;
    }
}
