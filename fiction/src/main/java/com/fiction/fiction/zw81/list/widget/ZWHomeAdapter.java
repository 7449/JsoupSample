package com.fiction.fiction.zw81.list.widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.fiction.fiction.zw81.list.model.ZWHomeModel;
import com.framework.base.SuperViewHolder;

import java.util.List;

/**
 * by y on 2017/4/20
 */

class ZWHomeAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    private List<ZWHomeModel> list = null;

    ZWHomeAdapter(List<ZWHomeModel> list) {
        this.list = list;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    void addAll(List<ZWHomeModel> list) {
        if (this.list != null) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    void removeAll() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }
}
