package com.movie.movie.piaohua.list.widget;

import android.text.Html;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.movie.R;
import com.movie.movie.piaohua.list.model.PiaoHuaListModel;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

class PiaoHuaListAdapter extends BaseRecyclerAdapter<PiaoHuaListModel> {

    PiaoHuaListAdapter(List<PiaoHuaListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_piao_hua;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, PiaoHuaListModel mDatas) {
        viewHolder.setTextView(R.id.piaohua_item_tv, Html.fromHtml(mDatas.title));
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.piaohua_item_iv), mDatas.url);
    }

}
