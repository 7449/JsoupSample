package com.image.adapter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.TouchImageView;
import com.image.mvp.model.ImageModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

public class ImageDetailAdapter extends BasePagerAdapter<ImageModel> {


    public ImageDetailAdapter(List<ImageModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, ImageModel data) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return imageView;
    }
}
