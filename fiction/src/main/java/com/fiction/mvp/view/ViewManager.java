package com.fiction.mvp.view;

import com.fiction.mvp.model.FictionModel;
import com.framework.base.mvp.BaseListView;
import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.BaseView;

import java.util.List;

/**
 * by y on 2017/5/23.
 */

public interface ViewManager {
    interface FictionContentsView extends BaseView<List<FictionModel>> {
    }

    interface FictionDetailView extends BaseView<FictionModel> {
    }

    interface FictionHomeView extends BaseView<List<FictionModel>> {
    }

    interface FictionListView extends BaseView<List<FictionModel>> {
    }

    interface SearchListView extends BaseListView<List<FictionModel>> {
        void fictionNameEmpty();
    }

    interface MainView extends BaseView<BaseModel> {
        void switchSearch();

        void switch81();

        void switchKsw();

        void switchBiQuGe();

    }
}
