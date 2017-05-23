package com.fiction.mvp.presenter;

import android.support.annotation.MenuRes;

import com.fiction.R;
import com.fiction.mvp.view.ViewManager;


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
            case R.id.search:
                mainView.switchSearch();
                break;
            case R.id.fiction_81:
                mainView.switch81();
                break;
            case R.id.bi_qu_ge:
                mainView.switchBiQuGe();
                break;
            case R.id.ksw:
                mainView.switchKsw();
                break;
        }
    }
}
