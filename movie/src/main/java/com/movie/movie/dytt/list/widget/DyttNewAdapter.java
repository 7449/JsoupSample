package com.movie.movie.dytt.list.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.movie.R;
import com.movie.movie.dytt.list.model.DyttNewModel;

import java.util.List;

/**
 * by y on 2017/3/23
 */

class DyttNewAdapter extends BaseRecyclerAdapter<DyttNewModel> {


    DyttNewAdapter(List<DyttNewModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dytt_new;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, DyttNewModel mDatas) {
        viewHolder.setTextView(R.id.tv_dytt_new, mDatas.title);
    }
}
