package com.image.image.douban.list.widget;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.image.douban.list.model.DouBanListModel;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

class DouBanListAdapter extends BaseRecyclerAdapter<DouBanListModel> {

    DouBanListAdapter(List<DouBanListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_douban_list;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, DouBanListModel mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.image), mDatas.url);
    }
}
