package com.movie.movie.xiaopian.list.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.movie.R;
import com.movie.movie.xiaopian.list.model.XiaoPianListModel;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

class XiaoPianListAdapter extends BaseRecyclerAdapter<XiaoPianListModel> {

    XiaoPianListAdapter(List<XiaoPianListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_xiaopian_main;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, XiaoPianListModel mDatas) {
        viewHolder.setTextView(R.id.xiaopian_item_content, mDatas.title);
    }

}
