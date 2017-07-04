package com.image.mvp.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.framework.base.mvp.BaseListView;
import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.BaseView;
import com.image.mvp.model.ImageModel;

import java.util.List;

/**
 * by y on 2017/5/23.
 */

public interface ViewManager {

    interface ImageDetailView extends BaseView<List<ImageModel>> {
        @NonNull
        String getType();
    }

    interface TagView extends BaseView<List<ImageModel>> {
        int getTabPosition();
    }

    interface TagImageListView extends BaseListView<List<ImageModel>> {
    }

    interface ImageListView extends BaseListView<List<ImageModel>> {
        @NonNull
        String getType();
    }

    interface SearchListView extends BaseListView<List<ImageModel>> {

    }

    interface MainView extends BaseView<BaseModel> {

        AppCompatActivity getMainActivity();

        void selectMenuFirst();

        void onBack();

        void switchSearch();
    }

}
