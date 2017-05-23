package com.image.image.meizitu.list.widget;


import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.image.meizitu.list.model.MeiZiTuListModel;

import java.util.List;

/**
 * by y on 2016/9/26.
 */

class MeiZiTuListAdapter extends BaseRecyclerAdapter<MeiZiTuListModel> {


    MeiZiTuListAdapter(List<MeiZiTuListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_meizitu_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MeiZiTuListModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }

}
