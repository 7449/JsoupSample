package com.fiction.mvp.presenter;

import android.support.annotation.MenuRes;

import com.fiction.R;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;


/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl  extends PresenterImplCompat<BaseModel,ViewManager.MainView> implements PresenterManager.MainPresenter {


    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.search:
                view.switchSearch();
                break;
            case R.id.fiction_81:
                view.switch81();
                break;
            case R.id.bi_qu_ge:
                view.switchBiQuGe();
                break;
            case R.id.ksw:
                view.switchKsw();
                break;
        }
    }

    @Override
    public BaseModel getT(Document document) {
        return null;
    }
}
