package com.fiction.fiction.biquge.list.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.fiction.R;
import com.fiction.fiction.biquge.list.model.BiQuGeHomeModel;
import com.fiction.fiction.contents.widget.FictionContentsActivity;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupBiQuGeHomeManager;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.rxjsoupnetwork.bus.RxBus;

import java.util.List;

/**
 * by y on 2017/4/21
 */

class BiQuGeHomeAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    private List<BiQuGeHomeModel> list = null;
    private String type = ApiConfig.Type.ZW_81;

    BiQuGeHomeAdapter(List<BiQuGeHomeModel> list, String type) {
        this.list = list;
        this.type = type;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case JsoupBiQuGeHomeManager.TYPE_HEADER:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_biquge_home_header));
            case JsoupBiQuGeHomeManager.TYPE_TITLE:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_biquge_home_title));
            default:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_biquge_home_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        BiQuGeHomeModel biQuGeHomeModel = list.get(position);
        switch (getItemViewType(position)) {
            case JsoupBiQuGeHomeManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, biQuGeHomeModel.title);
                holder.setTextView(R.id.tv_content, biQuGeHomeModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), biQuGeHomeModel.url);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, biQuGeHomeModel.detailUrl, biQuGeHomeModel.title));

                break;
            case JsoupBiQuGeHomeManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, biQuGeHomeModel.title);
                holder.itemView.setOnClickListener(v -> RxBus.getInstance().send(ApiConfig.Type.BI_QU_GE, biQuGeHomeModel.title));
                break;
            case JsoupBiQuGeHomeManager.TYPE_HOT:
            case JsoupBiQuGeHomeManager.TYPE_CENTER:
            case JsoupBiQuGeHomeManager.TYPE_RECENT:
            case JsoupBiQuGeHomeManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, biQuGeHomeModel.title);
                holder.itemView.setOnClickListener(v -> FictionContentsActivity.getInstance(type, biQuGeHomeModel.detailUrl, biQuGeHomeModel.title));
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

    void addAll(List<BiQuGeHomeModel> list) {
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
            if (itemViewType == JsoupBiQuGeHomeManager.TYPE_HEADER
                    || itemViewType == JsoupBiQuGeHomeManager.TYPE_TITLE) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}
