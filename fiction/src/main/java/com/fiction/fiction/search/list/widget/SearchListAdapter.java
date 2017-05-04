package com.fiction.fiction.search.list.widget;

import com.fiction.R;
import com.fiction.fiction.search.list.model.SearchListModel;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;

import java.util.List;

/**
 * by y on 2017/3/25.
 */

class SearchListAdapter extends BaseRecyclerAdapter<SearchListModel> {

    SearchListAdapter(List<SearchListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, SearchListModel mDatas) {
        ImageLoaderUtils.INSTANCE.display(viewHolder.getImageView(R.id.search_iv_img), mDatas.getUrl());
        viewHolder.setTextView(R.id.search_tv_title, mDatas.getTitle());
        viewHolder.setTextView(R.id.search_tv_content, mDatas.getMessage());
    }
}
