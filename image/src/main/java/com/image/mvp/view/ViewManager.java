package com.image.mvp.view;

import com.framework.base.mvp.BaseListView;
import com.framework.base.mvp.BaseView;
import com.image.mvp.model.ImageModel;

import java.util.List;

/**
 * by y on 2017/5/23.
 */

public interface ViewManager {

    interface ImageDetailView extends BaseView<List<ImageModel>> {
        void reverse();
    }

    interface ImageListView extends BaseListView<List<ImageModel>> {
    }

    interface SearchListView extends BaseListView<List<ImageModel>> {

    }

    interface MainView {

        void switchDouban();

        void switchMZitu();

        void switchMM();

        void switchMeiZiTu();

        void switch7KK();

        void switchCollection();

    }

}
