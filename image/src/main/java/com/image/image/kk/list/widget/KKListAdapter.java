package com.image.image.kk.list.widget;


import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.image.kk.list.model.KKListModel;

import java.util.List;

/**
 * by y on 2016/9/26.
 */

class KKListAdapter extends BaseRecyclerAdapter<KKListModel> {


    KKListAdapter(List<KKListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_kk_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, KKListModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }

}
