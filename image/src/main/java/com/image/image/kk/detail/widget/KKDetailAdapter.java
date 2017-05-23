package com.image.image.kk.detail.widget;

import android.view.ViewGroup;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.TouchImageView;
import com.image.image.kk.detail.model.KKDetailModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

class KKDetailAdapter extends BasePagerAdapter<KKDetailModel> {


    KKDetailAdapter(List<KKDetailModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, KKDetailModel data) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView);
        return imageView;
    }
}
