package com.movie.mvp.presenter;


import android.support.annotation.MenuRes;

import com.movie.R;
import com.movie.mvp.view.ViewManager;

/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl implements PresenterManager.MainPresenter {

    private final ViewManager.MainView mainView;

    public MainPresenterImpl(ViewManager.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.dytt:
                mainView.switchDytt();
                break;
            case R.id.dy_2018:
                mainView.swichDy2018();
                break;
            case R.id.xiaopian:
                mainView.switchXiaoPian();
                break;
            case R.id.piaohua:
                mainView.switchPiaoHua();
                break;
        }
    }
}
