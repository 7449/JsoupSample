package com.fiction.mian.presenter;

import android.support.annotation.MenuRes;

import com.fiction.R;
import com.fiction.mian.view.MainView;


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
            case R.id.search:
                mainView.switchSearch();
                break;
            case R.id.fiction_81:
                mainView.switch81();
                break;
            case R.id.bi_qu_ge:
                mainView.switchBiQuGe();
                break;
        }
    }
}
