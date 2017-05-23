package com.image.image.mm.list.widget;


import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.image.mm.list.model.MMListModel;

import java.util.List;

/**
 * by y on 2016/9/26.
 */

class MMListAdapter extends BaseRecyclerAdapter<MMListModel> {


    MMListAdapter(List<MMListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_mm_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MMListModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }

}
