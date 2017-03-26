package com.image.image.mzitu.detail.widget;

import android.view.ViewGroup;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.TouchImageView;
import com.image.image.mzitu.detail.model.MZiTuDetailModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

class MZiTuDetailAdapter extends BasePagerAdapter<MZiTuDetailModel> {


    MZiTuDetailAdapter(List<MZiTuDetailModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, MZiTuDetailModel data) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView);
        return imageView;
    }
}
