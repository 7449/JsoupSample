package com.magnetic.adapter;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.magnetic.R;
import com.magnetic.mvp.model.MagneticModel;

import java.util.List;

/**
 * by y on 2017/4/28
 */

public class MagneticListAdapter extends BaseRecyclerAdapter<MagneticModel> {

    public MagneticListAdapter(List<MagneticModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_magnetic_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MagneticModel mDatas) {
        viewHolder.setTextView(R.id.list_tv, mDatas.message);
    }
}
