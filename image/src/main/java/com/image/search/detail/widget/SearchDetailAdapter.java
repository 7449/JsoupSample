package com.image.search.detail.widget;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.widget.TouchImageView;
import com.image.search.detail.model.SearchDetailModel;

import java.util.List;


/**
 * by y on 2016/9/26.
 */

class SearchDetailAdapter extends BasePagerAdapter<SearchDetailModel> {


    SearchDetailAdapter(List<SearchDetailModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, SearchDetailModel data) {
        TouchImageView imageView = new TouchImageView(container.getContext());
        ImageLoaderUtils.display(imageView, data.url);
        container.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return imageView;
    }
}
