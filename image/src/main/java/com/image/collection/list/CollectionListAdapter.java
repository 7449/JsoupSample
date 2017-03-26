package com.image.collection.list;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.collection.sql.CollectionModel;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

class CollectionListAdapter extends BaseRecyclerAdapter<CollectionModel> {

    CollectionListAdapter(List<CollectionModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_collection;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, CollectionModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.getUrl());
    }
}
