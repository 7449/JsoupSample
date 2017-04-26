package com.fiction.fiction.ksw.list.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.fiction.R;
import com.fiction.fiction.contents.widget.FictionContentsActivity;
import com.fiction.fiction.ksw.list.model.KswListModel;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupKswListManager;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;

import java.util.List;

/**
 * by y on 2017/4/20
 */

class KswListAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    private List<KswListModel> list = null;
    private String type = ApiConfig.Type.ZW_81;

    KswListAdapter(List<KswListModel> list, String type) {
        this.list = list;
        this.type = type;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case JsoupKswListManager.TYPE_HEADER:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_ksw_list_header));
            case JsoupKswListManager.TYPE_TITLE:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_ksw_list_title));
            default:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_ksw_list_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        KswListModel kswListModel = list.get(position);
        switch (getItemViewType(position)) {
            case JsoupKswListManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, kswListModel.title);
                holder.setTextView(R.id.tv_content, kswListModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), kswListModel.url);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, kswListModel.detailUrl, kswListModel.title));

                break;
            case JsoupKswListManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, kswListModel.title);
                break;
            case JsoupKswListManager.TYPE_UPDATE:
            case JsoupKswListManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, kswListModel.title);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, kswListModel.detailUrl, kswListModel.title));
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

    void addAll(List<KswListModel> list) {
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
            if (itemViewType == JsoupKswListManager.TYPE_HEADER
                    || itemViewType == JsoupKswListManager.TYPE_TITLE) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}
