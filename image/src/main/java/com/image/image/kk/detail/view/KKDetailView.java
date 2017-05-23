package com.image.image.kk.detail.view;

import com.framework.base.mvp.BaseView;
import com.image.image.kk.detail.model.KKDetailModel;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public interface KKDetailView extends BaseView<List<KKDetailModel>> {
    void reverse();
}
