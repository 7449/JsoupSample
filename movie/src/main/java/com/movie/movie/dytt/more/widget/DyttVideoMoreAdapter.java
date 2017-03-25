package com.movie.movie.dytt.more.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.movie.R;
import com.movie.movie.dytt.more.model.DyttVideoMoreModel;

import java.util.List;

/**
 * by y on 2017/3/24
 */

class DyttVideoMoreAdapter extends BaseRecyclerAdapter<DyttVideoMoreModel> {

    DyttVideoMoreAdapter(List<DyttVideoMoreModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dytt_more_video;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, DyttVideoMoreModel mDatas) {
        viewHolder.setTextView(R.id.dytt_item_content, mDatas.title);
    }
}
