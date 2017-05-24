package com.magnetic.mvp.presenter;

import android.support.annotation.MenuRes;

import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.PresenterImplCompat;
import com.magnetic.R;
import com.magnetic.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/4/28
 */

public class MainPresenterImpl extends PresenterImplCompat<BaseModel, ViewManager.MainView> implements PresenterManager.MainPresenter {


    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.magnetic:
                view.switchMagnetic();
                break;
        }
    }

    @Override
    public BaseModel getT(Document document) {
        return null;
    }
}
