package com.fiction.fiction.list.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.fiction.R;
import com.fiction.fiction.contents.widget.FictionContentsActivity;
import com.fiction.fiction.list.model.FictionListModel;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionListManager;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;

import java.util.List;

/**
 * by y on 2017/4/20
 */

class FictionListAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    private List<FictionListModel> list = null;
    private String type = ApiConfig.Type.ZW_81;

    FictionListAdapter(List<FictionListModel> list, String type) {
        this.list = list;
        this.type = type;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case JsoupFictionListManager.TYPE_HEADER:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_fiction_list_header));
            case JsoupFictionListManager.TYPE_TITLE:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_fiction_list_title));
            default:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_fiction_list_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        FictionListModel biqugeListModel = list.get(position);
        switch (getItemViewType(position)) {
            case JsoupFictionListManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, biqugeListModel.title);
                holder.setTextView(R.id.tv_content, biqugeListModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), biqugeListModel.url);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, biqugeListModel.detailUrl, biqugeListModel.title));

                break;
            case JsoupFictionListManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, biqugeListModel.title);
                break;
            case JsoupFictionListManager.TYPE_UPDATE:
            case JsoupFictionListManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, biqugeListModel.title);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, biqugeListModel.detailUrl, biqugeListModel.title));
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

    void addAll(List<FictionListModel> list) {
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
            if (itemViewType == JsoupFictionListManager.TYPE_HEADER
                    || itemViewType == JsoupFictionListManager.TYPE_TITLE) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}
