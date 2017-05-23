package com.fiction.adapter;

import com.fiction.R;
import com.fiction.mvp.model.FictionModel;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;

import java.util.List;

/**
 * by y on 2017/3/25.
 */

public class SearchListAdapter extends BaseRecyclerAdapter<FictionModel> {

   public SearchListAdapter(List<FictionModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, FictionModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.search_iv_img), mDatas.url);
        viewHolder.setTextView(R.id.search_tv_title, mDatas.title);
        viewHolder.setTextView(R.id.search_tv_content, mDatas.message);
    }
}
