package com.movie.adapter;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.movie.R;
import com.movie.mvp.model.MovieModel;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class XiaoPianListAdapter extends BaseRecyclerAdapter<MovieModel> {

    public XiaoPianListAdapter(List<MovieModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_xiaopian_main;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MovieModel mDatas) {
        viewHolder.setTextView(R.id.xiaopian_item_content, mDatas.title);
    }

}
