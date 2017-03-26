package com.image.image.meizitu.detail.widget;

import android.view.ViewGroup;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.TouchImageView;
import com.image.image.meizitu.detail.model.MeiZiTuDetailModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

class MeiZiTuDetailAdapter extends BasePagerAdapter<MeiZiTuDetailModel> {


    MeiZiTuDetailAdapter(List<MeiZiTuDetailModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, MeiZiTuDetailModel data) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView);
        return imageView;
    }
}
