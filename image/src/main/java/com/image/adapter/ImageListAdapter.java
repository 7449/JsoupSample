package com.image.adapter;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.mvp.model.ImageModel;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class ImageListAdapter extends BaseRecyclerAdapter<ImageModel> {

    public ImageListAdapter(List<ImageModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_image_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, ImageModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }
}
