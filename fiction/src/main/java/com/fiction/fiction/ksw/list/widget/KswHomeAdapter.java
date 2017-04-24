package com.fiction.fiction.ksw.list.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.fiction.R;
import com.fiction.fiction.ksw.contents.widget.KswHomeContentsActivity;
import com.fiction.fiction.ksw.list.model.KswHomeModel;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupKswHomeManager;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.rxjsoupnetwork.bus.RxBus;

import java.util.List;

/**
 * by y on 2017/4/21
 */

class KswHomeAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    private List<KswHomeModel> list = null;

    KswHomeAdapter(List<KswHomeModel> list) {
        this.list = list;
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case JsoupKswHomeManager.TYPE_HEADER:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_ksw_home_header));
            case JsoupKswHomeManager.TYPE_TITLE:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_ksw_home_title));
            default:
                return new SuperViewHolder(UIUtils.getAdapterView(parent, R.layout.item_ksw_home_item));
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (list == null) {
            return;
        }
        KswHomeModel kswHomeModel = list.get(position);
        switch (getItemViewType(position)) {
            case JsoupKswHomeManager.TYPE_HEADER:

                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.setTextView(R.id.tv_content, kswHomeModel.message);
                ImageLoaderUtils.display(holder.getImageView(R.id.image), kswHomeModel.url);
                holder.itemView.setOnClickListener(v -> KswHomeContentsActivity.getInstance(kswHomeModel.detailUrl, kswHomeModel.title));

                break;
            case JsoupKswHomeManager.TYPE_TITLE:
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.itemView.setOnClickListener(v -> RxBus.getInstance().send(ApiConfig.Type.BI_QU_GE, kswHomeModel.title));
                break;
            case JsoupKswHomeManager.TYPE_HOT:
            case JsoupKswHomeManager.TYPE_CENTER:
            case JsoupKswHomeManager.TYPE_RECENT:
            case JsoupKswHomeManager.TYPE_ADD:
                holder.setTextView(R.id.tv_title, kswHomeModel.title);
                holder.itemView.setOnClickListener(v -> KswHomeContentsActivity.getInstance(kswHomeModel.detailUrl, kswHomeModel.title));
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

    void addAll(List<KswHomeModel> list) {
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
            if (itemViewType == JsoupKswHomeManager.TYPE_HEADER
                    || itemViewType == JsoupKswHomeManager.TYPE_TITLE) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}
