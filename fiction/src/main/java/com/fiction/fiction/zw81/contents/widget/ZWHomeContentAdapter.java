package com.fiction.fiction.zw81.contents.widget;

import android.support.v4.content.ContextCompat;

import com.fiction.R;
import com.fiction.fiction.zw81.contents.model.ZWHomeContentsModel;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;

import java.util.List;

/**
 * by y on 2017/3/25.
 */

class ZWHomeContentAdapter extends BaseRecyclerAdapter<ZWHomeContentsModel> {

    ZWHomeContentAdapter(List<ZWHomeContentsModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_zw_home_contents;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, ZWHomeContentsModel mDatas) {
        if (position < 10) {
            viewHolder.setTextColor(R.id.contents_tv_, ContextCompat.getColor(viewHolder.getContext(), R.color.colorAccent));
        } else {
            viewHolder.setTextColor(R.id.contents_tv_, ContextCompat.getColor(viewHolder.getContext(), R.color.black));
        }
        viewHolder.setTextView(R.id.contents_tv_, mDatas.title);
    }
}
