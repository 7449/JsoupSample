package com.image.image.mm.detail.widget;

import android.view.ViewGroup;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.TouchImageView;
import com.image.image.mm.detail.model.MMDetailModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

class MMDetailAdapter extends BasePagerAdapter<MMDetailModel> {


    MMDetailAdapter(List<MMDetailModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, MMDetailModel data) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView);
        return imageView;
    }
}
