package com.image.image.list.view;

import com.framework.base.BaseView;
import com.image.image.list.model.ImageListModel;

import java.util.List;


/**
 * by y on 2016/7/28.
 */
public interface ImageView extends BaseView<List<ImageListModel>> {
    String getType();
}
