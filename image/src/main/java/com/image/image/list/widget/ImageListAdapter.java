package com.image.image.list.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.image.list.model.ImageListModel;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

class ImageListAdapter extends BaseRecyclerAdapter<ImageListModel> {

    ImageListAdapter(List<ImageListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_image_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, ImageListModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }
}
