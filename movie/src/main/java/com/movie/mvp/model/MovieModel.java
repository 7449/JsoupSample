package com.movie.mvp.model;

import com.framework.base.mvp.BaseModel;
import com.xadapter.adapter.multi.MultiCallBack;

/**
 * by y on 2017/3/24
 */

public class MovieModel extends BaseModel implements MultiCallBack {
    public int type;
    public int itemType;

    @Override
    public int getItemType() {
        return type;
    }

    @Override
    public int getPosition() {
        return -1;
    }
}
