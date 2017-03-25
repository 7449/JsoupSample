package com.framework.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/7/26.
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

    private List<T> data = new ArrayList<>();

    public BasePagerAdapter(List<T> datas) {
        this.data = datas;
    }


    public void clearAll() {
        data.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        return instantiate(container, position, data.get(position));
    }

    protected abstract Object instantiate(ViewGroup container, int position, T data);

}

