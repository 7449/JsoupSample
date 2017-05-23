package com.image.image.mzitu.list.widget;


import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.image.mzitu.list.model.MZiTuListModel;

import java.util.List;

/**
 * by y on 2016/9/26.
 */

class MZiTuListAdapter extends BaseRecyclerAdapter<MZiTuListModel> {


    MZiTuListAdapter(List<MZiTuListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_mzitu_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MZiTuListModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }

}
