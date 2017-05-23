package com.magnetic.mvp.view;

import com.framework.base.mvp.BaseListView;
import com.magnetic.mvp.model.MagneticModel;

import java.util.List;

/**
 * by y on 2017/5/23.
 */

public interface ViewManager {
    interface MagneticListView extends BaseListView<List<MagneticModel>> {
    }

    interface MainView {
        void switchMagnetic();
    }
}
