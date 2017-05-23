package com.magnetic.mvp.presenter;

import android.support.annotation.MenuRes;

import com.magnetic.R;
import com.magnetic.mvp.view.ViewManager;

/**
 * by y on 2017/4/28
 */

public class MainPresenterImpl implements PresenterManager.MainPresenter {

    private final ViewManager.MainView mainView;

    public MainPresenterImpl(ViewManager.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.magnetic:
                mainView.switchMagnetic();
                break;
        }
    }
}
