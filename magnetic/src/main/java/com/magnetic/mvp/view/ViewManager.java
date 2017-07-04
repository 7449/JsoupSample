package com.magnetic.mvp.view;

import android.support.v4.app.FragmentActivity;

import com.framework.base.mvp.BaseListView;
import com.framework.base.mvp.BaseView;
import com.magnetic.mvp.model.MagneticModel;

import java.util.List;

/**
 * by y on 2017/6/6.
 */

public interface ViewManager {
    interface MainView extends BaseView<MagneticModel> {
        FragmentActivity getMainActivity();

        void onBack();

        void setToolBar();
    }

    interface MagneticListView extends BaseListView<List<MagneticModel>> {
        int getTabPosition();

        void zhizhuMagnetic(MagneticModel magneticModel);
    }
}
