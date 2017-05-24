package com.movie.mvp.presenter;


import android.support.annotation.MenuRes;

import com.framework.base.mvp.BaseModel;
import com.framework.base.mvp.PresenterImplCompat;
import com.movie.R;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/22
 */

public class MainPresenterImpl extends PresenterImplCompat<BaseModel,ViewManager.MainView> implements PresenterManager.MainPresenter {


    public MainPresenterImpl(ViewManager.MainView view) {
        super(view);
    }

    @Override
    public void switchId(@MenuRes int id) {
        switch (id) {
            case R.id.dytt:
                view.switchDytt();
                break;
            case R.id.dy_2018:
                view.swichDy2018();
                break;
            case R.id.xiaopian:
                view.switchXiaoPian();
                break;
            case R.id.piaohua:
                view.switchPiaoHua();
                break;
        }
    }

    @Override
    public BaseModel getT(Document document) {
        return null;
    }
}
