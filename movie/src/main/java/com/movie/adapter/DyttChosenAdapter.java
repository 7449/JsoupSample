package com.movie.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.framework.base.SuperViewHolder;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.ui.activity.DyttVideoMoreActivity;
import com.movie.ui.activity.DyttXLMoreActivity;
import com.movie.mvp.model.MovieModel;
import com.movie.ui.activity.DyttVideoDetailActivity;

import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttChosenAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    private static final int TYPE_HEADER = 0;

    public List<MovieModel> list = null;

    public DyttChosenAdapter(List<MovieModel> list) {
        this.list = list;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_dytt_chosen_header));
            default:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_dytt_chosen_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                holder.setTextView(R.id.dytt_tv_title, list.get(position).title);
                holder.itemView.setOnClickListener(v -> {
                    switch (list.get(position).itemType) {
                        case DyttJsoupManager.TYPE_2016:
                        case DyttJsoupManager.TYPE_ZH:
                        case DyttJsoupManager.TYPE_US:
                        case DyttJsoupManager.TYPE_TV:
                        case DyttJsoupManager.TYPE_ACG:
                            DyttVideoMoreActivity.startIntent(list.get(position).itemType, ApiConfig.Type.DYTT_CHOSEN_TYPE, list.get(position).title);
                            break;
                        case DyttJsoupManager.TYPE_XL:
                            DyttXLMoreActivity.startIntent(list.get(position).title);
                            break;
                    }
                });
                break;
            default:
                holder.setTextView(R.id.dytt_item_content, list.get(position).title);
                holder.itemView.setOnClickListener(v -> {
                    if (ApkUtils.getXLIntent() != null) {
                        DyttVideoDetailActivity.startIntent(list.get(position).url);
                    } else {
                        UIUtils.toast(UIUtils.getString(R.string.xl));
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

    public void addAll(List<MovieModel> list) {
        if (this.list != null) {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void removeAll() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }
}
