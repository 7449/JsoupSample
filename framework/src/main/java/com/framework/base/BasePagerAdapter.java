package com.framework.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.framework.base.mvp.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2016/7/26.
 */
public abstract class BasePagerAdapter<T extends BaseModel> extends PagerAdapter {

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

    public List<T> getListData() {
        return data;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public T getData(int position) {
        return data.isEmpty() ? null : data.get(position);
    }

    public String getUrl(int position) {
        return getData(position) == null ? null : getData(position).url;
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

