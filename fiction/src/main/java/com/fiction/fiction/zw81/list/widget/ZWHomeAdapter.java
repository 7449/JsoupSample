package com.fiction.fiction.zw81.list.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.fiction.R;
import com.fiction.fiction.zw81.contents.widget.ZWHomeContentsActivity;
import com.fiction.fiction.zw81.list.model.ZWHomeModel;
import com.fiction.manager.JsoupZwHomeManager;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.rxjsoupnetwork.bus.RxBus;

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
        switch (viewType) {
            case JsoupZwHomeManager.TYPE_HEADER:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_zw_home_header));
            case JsoupZwHomeManager.TYPE_TITLE:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_zw_home_title));
            default:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_zw_home_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        ZWHomeModel zwHomeModel = list.get(position);
        switch (getItemViewType(position)) {
            case JsoupZwHomeManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, zwHomeModel.title);
                holder.setTextView(R.id.tv_content, zwHomeModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), zwHomeModel.url);
                holder.itemView.setOnClickListener(v -> ZWHomeContentsActivity.getInstance(zwHomeModel.detailUrl, zwHomeModel.title));

                break;
            case JsoupZwHomeManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, zwHomeModel.title);
                holder.itemView.setOnClickListener(v -> RxBus.getInstance().send(JsoupZwHomeManager.TYPE_HEADER, zwHomeModel.title));
                break;
            case JsoupZwHomeManager.TYPE_PUSH:
            case JsoupZwHomeManager.TYPE_CENTER:
            case JsoupZwHomeManager.TYPE_RECENT:
            case JsoupZwHomeManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, zwHomeModel.title);
                holder.itemView.setOnClickListener(v -> ZWHomeContentsActivity.getInstance(zwHomeModel.detailUrl, zwHomeModel.title));
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

    @Override
    public void onViewAttachedToWindow(SuperViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams stagger = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            int itemViewType = getItemViewType(holder.getLayoutPosition());
            if (itemViewType == JsoupZwHomeManager.TYPE_HEADER
                    || itemViewType == JsoupZwHomeManager.TYPE_TITLE) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}
