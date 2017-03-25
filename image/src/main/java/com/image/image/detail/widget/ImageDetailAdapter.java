package com.image.image.detail.widget;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
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
        ImageView imageView = new ImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView);
        return imageView;
    }
}
