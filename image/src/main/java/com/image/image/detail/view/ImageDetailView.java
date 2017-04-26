package com.image.image.detail.view;

import com.framework.base.mvp.BaseView;
import com.image.image.detail.model.ImageDetailModel;

import java.util.List;


/**
 * by y on 2016/7/28.
 */
public interface ImageDetailView extends BaseView<List<ImageDetailModel>> {
    void reverse();
}

