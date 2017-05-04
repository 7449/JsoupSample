package com.image.image.detail.widget;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.TouchImageView;
import com.image.image.detail.model.ImageDetailModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

class ImageDetailAdapter extends BasePagerAdapter<ImageDetailModel> {


    ImageDetailAdapter(List<ImageDetailModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, ImageDetailModel data) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        ImageLoaderUtils.INSTANCE.display(imageView, data.getUrl());
        container.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return imageView;
    }
}
