package com.magnetic.mvp.presenter;

import android.support.annotation.NonNull;

/**
 * by y on 2017/5/23.
 */

public interface PresenterManager {
    interface MainPresenter {
        void startSearch(String search);

        void onMainDestroy();
    }

    interface MagneticListPresenter {
        void netWorkRequest(@NonNull String search, int tabPosition, int page);

        void netWorkZhiZhuMagnetic(@NonNull String url);
    }

}
