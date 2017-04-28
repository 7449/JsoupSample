package com.magnetic.main.presenter;

import android.support.annotation.MenuRes;

import com.magnetic.main.view.MainView;

/**
 * by y on 2017/4/28
 */

public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {

        }
    }
}
