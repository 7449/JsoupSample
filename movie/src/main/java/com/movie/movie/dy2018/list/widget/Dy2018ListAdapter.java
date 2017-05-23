package com.movie.movie.dy2018.list.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.movie.R;
import com.movie.movie.dy2018.list.model.Dy2018ListModel;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

class Dy2018ListAdapter extends BaseRecyclerAdapter<Dy2018ListModel> {

    Dy2018ListAdapter(List<Dy2018ListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dy2018_main;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, Dy2018ListModel mDatas) {
        viewHolder.setTextView(R.id.dy2018_item_content, mDatas.title);
    }
}
