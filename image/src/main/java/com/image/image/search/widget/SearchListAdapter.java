package com.image.image.search.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.image.search.model.SearchListModel;

import java.util.List;

/**
 * by y on 2017/4/19.
 */

class SearchListAdapter extends BaseRecyclerAdapter<SearchListModel> {


    SearchListAdapter(List<SearchListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_search_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, SearchListModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }

    int getTotalPage() {
        return mDatas == null ? 0 : mDatas.get(0).totalPage;
    }
}
