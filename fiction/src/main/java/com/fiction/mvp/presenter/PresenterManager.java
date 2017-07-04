package com.fiction.mvp.presenter;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {
    interface FictionContentsPresenter {
        void startContents(String url);
    }

    interface FictionDetailPresenter {
        void startDetail(String url);
    }

    interface FictionHomePresenter {
        void netWorkRequest(String type);
    }

    interface PtFictionHomePresenter {
        void netWorkRequest();
    }

    interface FictionListPresenter {
        void netWork(String type, int tabPosition);
    }

    interface PtFictionListPresenter {
        void netWork(int tabPosition, int page);
    }

    interface PtFictionMorePresenter {
        void netWork(@NonNull String url, int page);
    }

    interface SearchListPresenter {
        void startSearch(String fictionName, int page);
    }

    interface MainPresenter {
        void switchId(@MenuRes int id);

        void onBackPressed();

        void onMainDestroy();
    }

}
