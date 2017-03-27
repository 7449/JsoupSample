package com.image.collection.detail;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.framework.base.BaseDialogFragment;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.ExtendedViewPager;
import com.framework.widget.TouchImageView;
import com.image.R;
import com.image.collection.sql.CollectionModel;
import com.image.collection.sql.GreenDaoDbUtils;

import java.util.List;

/**
 * by y on 2017/3/27
 */

public class CollectionDetailDialog extends BaseDialogFragment {

    private int position = 0;
    private Toolbar toolbar;

    public CollectionDetailDialog init(int position) {
        this.position = position;
        return this;
    }

    public static void startFragment(int position, FragmentManager fragmentManager) {
        new CollectionDetailDialog().init(position).show(fragmentManager, "collectionDetail");
    }


    @Override
    public AlertDialog getDialog() {
        mRootView = getRootView(R.layout.collection_detail);

        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setView(mRootView)
                .create();

        ExtendedViewPager viewPager = getView(R.id.viewPager);
        toolbar = getView(R.id.toolbar);
        CollectionDetailAdapter adapter = new CollectionDetailAdapter(GreenDaoDbUtils.getCollectionAll());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);

        setTitles(position, adapter.getCount());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setTitles(position + 1, adapter.getCount());
            }
        });

        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return true;
    }

    private static class CollectionDetailAdapter extends PagerAdapter {

        private List<CollectionModel> list;

        CollectionDetailAdapter(List<CollectionModel> list) {
            this.list = list;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TouchImageView imageView = new TouchImageView(container.getContext());
            ImageLoaderUtils.display(imageView, list.get(position).getUrl());
            container.addView(imageView);
            return imageView;
        }
    }

    private void setTitles(int page, int imageSize) {
        if (toolbar != null)
            toolbar.setTitle(getString(R.string.collection_title) + "(" + page + "/" + imageSize + ")");
    }
}
