package com.image.mvp.presenter;

import android.support.annotation.MenuRes;

import com.image.R;
import com.image.mvp.view.ViewManager;

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
            case R.id.mzitu:
                mainView.switchMZitu();
                break;
            case R.id.dbmz:
                mainView.switchDouban();
                break;
            case R.id.mm:
                mainView.switchMM();
                break;
            case R.id.meizitu:
                mainView.switchMeiZiTu();
                break;
            case R.id.kk:
                mainView.switch7KK();
                break;
            case R.id.collection:
                mainView.switchCollection();
                break;
        }
    }
}
