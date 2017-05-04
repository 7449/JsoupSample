package com.movie.movie.dytt.more.widget;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.framework.base.SuperViewHolder;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.movie.dytt.detail.widget.DyttVideoDetailActivity;
import com.movie.movie.dytt.more.model.DyttXLMoreModel;

import java.util.List;

/**
 * by y on 2017/3/24
 */

class DyttXLMoreAdapter extends RecyclerView.Adapter<SuperViewHolder> {

    private static final int TYPE_HEADER = 0;
    private List<DyttXLMoreModel> list = null;

    DyttXLMoreAdapter(List<DyttXLMoreModel> list) {
        this.list = list;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new SuperViewHolder(UIUtils.INSTANCE.getAdapterView(parent, R.layout.item_dytt_more_xl_header));
            default:
                return new SuperViewHolder(UIUtils.INSTANCE.getAdapterView(parent, R.layout.item_dytt_more_xl_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                holder.setTextView(R.id.dytt_tv_title, list.get(position).getTitle());
                holder.itemView.setOnClickListener(v -> DyttVideoMoreActivity.startIntent(list.get(position).itemType, ApiConfig.Type.DYTT_XL_TYPE, list.get(position).getTitle()));
                break;
            default:
                holder.setTextView(R.id.dytt_item_content, list.get(position).getTitle());
                holder.itemView.setOnClickListener(v -> {
                    if (ApkUtils.INSTANCE.getXLIntent() != null) {
                        DyttVideoDetailActivity.startIntent(list.get(position).getUrl());
                    } else {
                        UIUtils.INSTANCE.toast(UIUtils.INSTANCE.getString(R.string.xl));
                    }
                });
                break;
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

    void addAll(List<DyttXLMoreModel> list) {
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
