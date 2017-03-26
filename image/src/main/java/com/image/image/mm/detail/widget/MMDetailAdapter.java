package com.image.image.mm.detail.widget;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
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
        ImageView imageView = new ImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView);
        return imageView;
    }
}
