package com.magnetic.magnetic.list.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.magnetic.R;
import com.magnetic.magnetic.list.model.MagneticListModel;

import java.util.List;

/**
 * by y on 2017/4/28
 */

class MagneticListAdapter extends BaseRecyclerAdapter<MagneticListModel> {

    MagneticListAdapter(List<MagneticListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_magnetic_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MagneticListModel mDatas) {
        viewHolder.setTextView(R.id.list_tv, mDatas.getMessage());
    }
}
