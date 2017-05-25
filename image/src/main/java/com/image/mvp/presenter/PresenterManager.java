package com.image.mvp.presenter;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {

    interface ImageDetailPresenter {
        void netWorkRequest(String type, String url);
    }

    interface ImageListPresenter {
        void netWorkRequest(String type, int id, int page);
    }

    interface MainPresenter {
        void switchId(@MenuRes int id);

        void onBackPressed();

        void onMainDestroy();
    }

    interface SearchListPresenter {
        void netWorkRequest(@NonNull String searchType, @NonNull String content, int page);
    }

}
