package com.movie.adapter;

import android.text.Html;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.framework.utils.ImageLoaderUtils;
import com.movie.R;
import com.movie.mvp.model.MovieModel;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class PiaoHuaListAdapter extends BaseRecyclerAdapter<MovieModel> {

    public PiaoHuaListAdapter(List<MovieModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_piao_hua;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MovieModel mDatas) {
        viewHolder.setTextView(R.id.piaohua_item_tv, Html.fromHtml(mDatas.title));
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.piaohua_item_iv), mDatas.url);
    }

}
