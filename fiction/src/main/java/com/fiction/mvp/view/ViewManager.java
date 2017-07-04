package com.fiction.mvp.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

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
        @NonNull
        String getType();
    }

    interface FictionDetailView extends BaseView<FictionModel> {
        @NonNull
        String getType();
    }

    interface FictionHomeView extends BaseView<List<FictionModel>> {
    }

    interface PtFictionHomeView extends BaseView<List<FictionModel>> {
    }

    interface FictionListView extends BaseView<List<FictionModel>> {
    }

    interface PtFictionListView extends BaseListView<List<FictionModel>> {
    }

    interface PtFictionMoreView extends BaseListView<List<FictionModel>> {
    }

    interface SearchListView extends BaseListView<List<FictionModel>> {
    }

    interface MainView extends BaseView<BaseModel> {
        void switchSearch();

        void switchMark();

        AppCompatActivity getMainActivity();

        void selectMenuFirst();

        void onBack();
    }
}
