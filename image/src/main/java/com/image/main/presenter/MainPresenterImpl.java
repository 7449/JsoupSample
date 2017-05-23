package com.image.main.presenter;

import android.support.annotation.MenuRes;

import com.image.R;
import com.image.main.view.MainView;

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
