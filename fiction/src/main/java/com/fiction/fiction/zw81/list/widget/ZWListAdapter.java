package com.fiction.fiction.zw81.list.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.fiction.R;
import com.fiction.fiction.zw81.contents.widget.ZWListContentsActivity;
import com.fiction.fiction.zw81.list.model.ZWListModel;
import com.fiction.manager.JsoupZwListManager;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;

import java.util.List;

/**
 * by y on 2017/4/20
 */

class ZWListAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    private List<ZWListModel> list = null;

    ZWListAdapter(List<ZWListModel> list) {
        this.list = list;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case JsoupZwListManager.TYPE_HEADER:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_zw_list_header));
            case JsoupZwListManager.TYPE_TITLE:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_zw_list_title));
            default:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_zw_list_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        ZWListModel zwListModel = list.get(position);
        switch (getItemViewType(position)) {
            case JsoupZwListManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, zwListModel.title);
                holder.setTextView(R.id.tv_content, zwListModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), zwListModel.url);
                holder.itemView.setOnClickListener(v -> ZWListContentsActivity.getInstance(zwListModel.detailUrl, zwListModel.title));

                break;
            case JsoupZwListManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, zwListModel.title);
                break;
            case JsoupZwListManager.TYPE_UPDATE:
            case JsoupZwListManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, zwListModel.title);
                holder.itemView.setOnClickListener(v -> ZWListContentsActivity.getInstance(zwListModel.detailUrl, zwListModel.title));
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

    void addAll(List<ZWListModel> list) {
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
            if (itemViewType == JsoupZwListManager.TYPE_HEADER
                    || itemViewType == JsoupZwListManager.TYPE_TITLE) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}
