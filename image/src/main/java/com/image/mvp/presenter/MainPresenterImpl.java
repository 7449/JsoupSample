package com.image.mvp.presenter;

import android.support.annotation.MenuRes;

import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.BasePresenterImpl;
import com.image.R;
import com.image.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl extends BasePresenterImpl<BaseModel,ViewManager.MainView> implements PresenterManager.MainPresenter {


    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.mzitu:
                view.switchMZitu();
                break;
            case R.id.dbmz:
                view.switchDouban();
                break;
            case R.id.mm:
                view.switchMM();
                break;
            case R.id.meizitu:
                view.switchMeiZiTu();
                break;
            case R.id.kk:
                view.switch7KK();
                break;
            case R.id.collection:
                view.switchCollection();
                break;
        }
    }

    @Override
    public BaseModel getT(Document document) {
        return null;
    }
}
