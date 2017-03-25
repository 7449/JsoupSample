package com.fiction.search.widget;

import com.fiction.R;
import com.fiction.search.model.SearchModel;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;

import java.util.List;

/**
 * by y on 2017/3/25.
 */

class SearchAdapter extends BaseRecyclerAdapter<SearchModel> {

    SearchAdapter(List<SearchModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, SearchModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.search_iv_img), mDatas.url);
        viewHolder.setTextView(R.id.search_tv_title, mDatas.title);
        viewHolder.setTextView(R.id.search_tv_content, mDatas.message);
    }
}
