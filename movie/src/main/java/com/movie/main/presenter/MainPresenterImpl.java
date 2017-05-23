package com.movie.main.presenter;


import android.support.annotation.MenuRes;

import com.movie.R;
import com.movie.main.view.MainView;

/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;

    public MainPresenterImpl(MainView mainView) {
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
