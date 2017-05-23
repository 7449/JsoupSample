package com.movie.adapter;

import com.framework.base.BaseRecyclerAdapter;
import com.framework.base.SuperViewHolder;
import com.movie.R;
import com.movie.mvp.model.MovieModel;

import java.util.List;

/**
 * by y on 2017/3/23
 */

public class DyttNewAdapter extends BaseRecyclerAdapter<MovieModel> {


    public DyttNewAdapter(List<MovieModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dytt_new;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, MovieModel mDatas) {
        viewHolder.setTextView(R.id.tv_dytt_new, mDatas.title);
    }
}
