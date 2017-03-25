package com.image.image.douban.detail.widget;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.image.image.douban.detail.model.DouBanDetailModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

class DouBanDetailAdapter extends BasePagerAdapter<DouBanDetailModel> {


    DouBanDetailAdapter(List<DouBanDetailModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, DouBanDetailModel data) {
        ImageView imageView = new ImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView);
        return imageView;
    }
}
