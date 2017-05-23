package com.fiction.mvp.presenter;

import android.support.annotation.MenuRes;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {
    interface FictionContentsPresenter {
        void startContents(String url, String type);
    }

    interface FictionDetailPresenter {
        void startDetail(String url, String type);
    }

    interface FictionHomePresenter {
        void netWorkRequest(String type);
    }

    interface FictionListPresenter {
        void netWork(String type, int tabPosition);
    }

    interface SearchListPresenter {
        void startSearch(String fictionName, int page, String searchType);
    }

    interface MainPresenter {
        void switchId(@MenuRes int id);
    }

}
