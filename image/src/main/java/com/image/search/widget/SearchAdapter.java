package com.image.search.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.search.model.SearchModel;

import java.util.List;

/**
 * by y on 2017/4/19.
 */

class SearchAdapter extends BaseRecyclerAdapter<SearchModel> {


    SearchAdapter(List<SearchModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, SearchModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }
}
