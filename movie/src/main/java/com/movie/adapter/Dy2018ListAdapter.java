package com.movie.adapter;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.movie.R;
import com.movie.mvp.model.MovieModel;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class Dy2018ListAdapter extends BaseRecyclerAdapter<MovieModel> {

    public Dy2018ListAdapter(List<MovieModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dy2018_main;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MovieModel mDatas) {
        viewHolder.setTextView(R.id.dy2018_item_content, mDatas.title);
    }
}
