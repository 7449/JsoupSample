package com.movie.mvp.presenter;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {
    interface VideoDetailPresenter {
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

    interface DyttVideoMorePresenter {
        void netWorkRequest(int type, int placeType, int page);
    }

    interface DyttXLMorePresenter {
        void netWorkRequest(String url);
    }

    interface PiaoHuaListPresenter {
        void netWorkRequest(int position, int page);
    }

    interface XiaoPianListPresenter {
        void netWorkRequest(int type, int page);
    }

    interface K567ListPresenter {
        void netWorkRequest(int type, int page);
    }

    interface K567DetailPresenter {
        void netWorkRequest(@NonNull String url);
    }

    interface MainPresenter {
        void switchId(@MenuRes int id);


        void onBackPressed();

        void onMainDestroy();
    }
}
