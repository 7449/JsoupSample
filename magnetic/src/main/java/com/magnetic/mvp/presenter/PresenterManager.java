package com.magnetic.mvp.presenter;

import android.support.annotation.MenuRes;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {
    interface MagneticListPresenter {
        void netWorkRequest(String content, int tabPosition, int page);
    }

    interface MainPresenter {
        void switchId(@MenuRes int id);
    }

}
