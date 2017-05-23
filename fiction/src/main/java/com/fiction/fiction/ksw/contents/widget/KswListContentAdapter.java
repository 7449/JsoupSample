package com.fiction.fiction.ksw.contents.widget;

import android.support.v4.content.ContextCompat;

import com.fiction.R;
import com.fiction.fiction.ksw.contents.model.KswListContentsModel;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;

import java.util.List;

/**
 * by y on 2017/3/25.
 */

class KswListContentAdapter extends BaseRecyclerAdapter<KswListContentsModel> {

    KswListContentAdapter(List<KswListContentsModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_ksw_list_contents;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, KswListContentsModel mDatas) {
        if (position < 10) {
            viewHolder.setTextColor(R.id.contents_tv_, ContextCompat.getColor(viewHolder.getContext(), R.color.colorAccent));
        } else {
            viewHolder.setTextColor(R.id.contents_tv_, ContextCompat.getColor(viewHolder.getContext(), R.color.black));
        }
        viewHolder.setTextView(R.id.contents_tv_, mDatas.title);
    }
}
