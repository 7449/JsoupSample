package com.image.adapter;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.mvp.model.ImageModel;

import java.util.List;

/**
 * by y on 2017/4/19.
 */

public class SearchListAdapter extends BaseRecyclerAdapter<ImageModel> {


    public SearchListAdapter(List<ImageModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, ImageModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }

    public int getTotalPage() {
        return mDatas == null ? 0 : mDatas.get(0).totalPage;
    }
}
