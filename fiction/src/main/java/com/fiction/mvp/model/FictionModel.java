package com.fiction.mvp.model;

import com.framework.base.mvp.BaseModel;
import com.xadapter.adapter.multi.MultiCallBack;

/**
 * by y on 2017/4/6.
 */

public class FictionModel extends BaseModel implements MultiCallBack {
    public String nextPage;
    public String onPage;
    public int type = -1;

    @Override
    public int getItemType() {
        return type;
    }

    @Override
    public int getPosition() {
        return -1;
    }
}
