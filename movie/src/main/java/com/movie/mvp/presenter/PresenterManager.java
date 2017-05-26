package com.movie.mvp.presenter;

import android.support.annotation.MenuRes;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {
    interface Dy2018DetailPresenter {
        void netWorkRequest(String url);
    }

    interface Dy2018ListPresenter {
        void netWorkRequest(int type, int page);
    }

    interface DyttChosenPresenter {
        void netWorkRequest();
    }

    interface DyttNewPresenter {
        void netWorkRequest();
    }

    interface DyttVideoDetailPresenter {
        void netWorkRequest(String url);
    }

    interface DyttVideoMorePresenter {
        void netWorkRequest(int type, int placeType, int page);
    }

    interface DyttXLMorePresenter {
        void netWorkRequest(String url);
    }

    interface PiaoHuaDetailPresenter {
        void netWorkRequest(String url);
    }

    interface PiaoHuaListPresenter {
        void netWorkRequest(int position, int page);
    }

    interface XiaoPianDetailPresenter {
        void netWorkRequest(String url);
    }

    interface XiaoPianListPresenter {
        void netWorkRequest(int type, int page);
    }

    interface MainPresenter {
        void switchId(@MenuRes int id);


        void onBackPressed();

        void onMainDestroy();
    }
}
